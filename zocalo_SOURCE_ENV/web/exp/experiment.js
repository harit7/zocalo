/*
Copyright 2007-2009 Chris Hibbert.  All rights reserved.
Copyright 2005 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
*/

// Functions referred to before being defined.  To make jslint stop complaining

/*global document, frames, setTimeout, clearTimeout, getNamedSubframe, endDateFromTimeString, timerTick, updateRound, getRound, getRoundLabel, updateTime, getTime, getDeleteOrdersInput, makeLabelDiv, updateDisableFlagInForm, bestBuyPrice, bestSellPrice, getNamedIFrame  */

/*jslint browser: true */

var liveTopic = "/liveUpdate";
var historicTopic = "/historicalUpdate";
var transitionTopic = "/transition";
var privateTopic = "/service/privateEvent";

var timerID = 0;
var finishTime = 0;

var defaultRoundLabel = "Round";
var roundLabel;

//  use console.debug() with firebug for debugging.

///////////// General support

function findChart() {
    var doc;
    var chartFrame = getNamedIFrame('stripchartframe');
    if (chartFrame === null) {
        doc = document;
    } else {
        doc = chartFrame.document;
    }
    return doc.getElementById('chart');
}

function malleableDocument() {
    return document.getElementById && document.createTextNode;
}

function updateStyle(element, styleChanges) {
    for (var name in styleChanges) {
        if (styleChanges.hasOwnProperty(name)) {
            element.style[name] = styleChanges[name];
        }
    }
}

function getNamedIFrame(name) {
    return getNamedSubframe(name, top);
}

function getNamedSubframe(name, frame) {
    if (frame === null) {
        return null;
    }
    for (var i = 0; i < frame.frames.length; i++) {
        var subFrame = frame.frames[i];
        var subframeName;
        try {
            subframeName = subFrame.name;
        } catch (exception) {
            subframeName = "";
        }
        if (subframeName == name) {
            return subFrame;
        } else {
            var f = getNamedSubframe(name, subFrame);
            if (f !== null) {
                return f;
            }
        }
    }
    return null;
}

function getPrices(numString) {
    if (null === numString || '' === numString || numString === undefined) {
        return [];
    }
    return numString.split(",");
}

function callHandlePrivateMessage(event) {
    if (top.handlePrivateMessage) {
        top.handlePrivateMessage(event);
    }
}

function callStartRoundActions() {
    if (top.startRoundActions) {
        top.startRoundActions();
    }
}

function callTransitionAction(transition) {
    if (top.transitionAction) {
        top.transitionAction(transition);
    }
}

function callHistoricalAction() {
    if (top.historicalAction) {
        top.historicalAction();
    }
}

function reloadSubSubframe(transition) {
    if (top.reloadMySubSubframe) {
        top.reloadMySubSubframe(transition);
    }
}

function updateBestPrices(event) {
    if (top.updateBestBuyPrice && top.updateBestSellPrice) {
        top.updateBestBuyPrice(bestBuyPrice(event));
        top.updateBestSellPrice(bestSellPrice(event));
    }
}

///////////// modifying offers display

function findOffers() {
    return document.getElementById('offers');
}

function addRowToTable(table, text, color) {
    var row = document.createElement("tr");
    var cell = row.insertCell(0);
    cell.innerHTML = text;
    row.align = "center";
    row.bgColor = color;
    table.appendChild(row);
}

function addRowsIncreasing(table, prices, color) {
    var priceList = getPrices(prices);
    for (var i = 0 ; i < priceList.length ; i++ ) {
        addRowToTable(table, priceList[i], color);
    }
}

function addRowsDecreasing(table, prices, color) {
    var priceList = getPrices(prices);
    for (var j = priceList.length - 1 ; j >= 0 ; j-- ) {
        addRowToTable(table, priceList[j], color);
    }
}

function makeOffersTable(desc) {
    var newTable = document.createElement("table");
    newTable.className = "offersTable";
    addRowToTable(newTable, "<b>Offers to Sell</b>", "");
    addRowsIncreasing(newTable, desc.sell, "orange");
    addRowToTable(newTable, "&nbsp;", "");
    addRowsDecreasing(newTable, desc.buy, "limegreen");
    addRowToTable(newTable, "<b>Offers to Buy</b>", "");
    return newTable;
}

function replaceCurrentOffers(offers, desc) {
    if (!malleableDocument()) {
        return;
    }

    if (offers.hasChildNodes()) {
        offers.removeChild(offers.lastChild);
    }
    var newOffers = makeOffersTable(desc);
    offers.appendChild(newOffers);
}

function clear_offers(offers) {
    replaceCurrentOffers(offers, { sell: "", buy: ""});
}

////////////////////// updating status messages

function makeP(message) {
    var newP = document.createElement("p");
    newP.className = "statusMessage";
    newP.innerHTML = message;
    newP.align = 'center';
    return newP;
}

function updateStatusSection(newStatus, sectionNum) {
    if (!malleableDocument()) {
        return;
    }
    var statusDiv = document.getElementById('experimentStatus');
    var existingStatus = statusDiv.getElementsByTagName('p')[0];

    var statusArray = existingStatus.innerHTML.split("<br>");
    statusArray[sectionNum] = newStatus;

    var newStatusString = statusArray[0] + "<br>" + statusArray[1] + "<br>" + statusArray[2];
    var newPara = makeP(newStatusString);
    statusDiv.replaceChild(newPara, existingStatus);
}

function getStatusSection(sectionNum) {
    var statusDiv = document.getElementById('experimentStatus');
    var existingStatus = statusDiv.getElementsByTagName('p')[0];
    var statusArray = existingStatus.innerHTML.split("<br>");
    return statusArray[sectionNum];
}

function updateStatus(status) {
    updateStatusSection(status, 0);
}

function updateTimeRemaining(event) {
    if (!malleableDocument()) {
        return;
    }
    updateRound(event);
    updateTime(event);
    if (event.timeRemaining && event.timeRemaining != "closed") {
        endDateFromTimeString(event.timeRemaining);
        timerTick();
    }
}

function endDateFromTimeString(when) {
    var now = new Date();
    var timeParts = when.split(":");
    var millisRemaining = 1000 * ((60 * timeParts[0]) + (1 * timeParts[1]));  // delicate javascript arithmetic
    finishTime = now.getTime() + (1 * millisRemaining);                       // delicate javascript arithmetic
    return new Date(finishTime);
}

function timerTick() {
    var now = new Date();
    clearTimeout(timerID);
    timerID = 0;
    if (now.getTime() >= finishTime - 2000) {
        updateTime( { timeRemaining: "finishing..." } );
    } else {
        var remaining = finishTime - now.getTime();
        var remainingTime = new Date(remaining);
        updateTime( { timeRemaining: remainingTime.toLocaleTimeString().substring(3,8) } );
        timerID = setTimeout(timerTick, 1000);
    }
}

function updateRound(event) {
    var row = getRound(event);
    updateStatusSection(row, 1);
}

function getRound(event) {
    var round;
    var roundLabel = getRoundLabel().toLowerCase();
    if (event.round) {
        round = "Current " + roundLabel + ": " + event.round;
    } else {
        round = "&nbsp;";
    }

    return round;
}

function getRoundLabel() {
    var doc;
    var subFrame = getNamedIFrame('subFrame');
    if (subFrame === null) {
        doc = document;
    } else {
        doc = subFrame.document;
    }

    var roundLabelDiv = doc.getElementById("roundLabel");
    roundLabel = (roundLabelDiv === null) ? defaultRoundLabel : roundLabelDiv.className;
    return roundLabel;
}

function updateTime(event) {
    var timeRemaining = getTime(event);
    updateStatusSection(timeRemaining, 2);
}

function getTime(event) {
    var time;
    var timeLeft = event.timeRemaining;
    if (timeLeft) {
        if (timeLeft == "closed") {
            time = getRoundLabel() + " is finished.";
            clearTimeout(timerID);
            timerID = 0;
        } else if (timeLeft.substring(timeLeft.length - 2) == " PM") {
            timeLeft = timeLeft.substring(0, timeLeft.length - 3);
            time = "Time Remaining: " + timeLeft;
        } else {
            time = "Time Remaining: " + timeLeft;
        }
    } else {
        time = "&nbsp;";
    }

    return time;
}

////////////////////// updating market order prices

function getNamedInput(formName, inputName) {
    var form = document.getElementById(formName);
    if (form === null) {
        return null;
    }
    var inputs = form.getElementsByTagName('input');
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].name == inputName) {
            return inputs[i];
        }
    }
    return null;
}

function updateInputValue(formName, inputName, newValue) {
    var input = getNamedInput(formName, inputName);
    if (input === null) {
        return;
    }
    if (newValue == "0" || newValue == top.chart.getMaxPrice()) {
        input.value = "";
        updateDisableFlagInForm(formName, "action", true);
    } else {
        input.value = newValue;
        updateDisableFlagInForm(formName, "action", false);
    }
}

function updateDisableFlagInForm(formId, valueName, newValue) {
    var form = document.getElementById(formId);
    if (form === null) {
        return;
    }
    form.elements[valueName].disabled = newValue;
}

function updateValueInForm(formId, valueName, newValue) {
    var form = document.getElementById(formId);
    if (form === null) {
        return;
    }
    form.elements[valueName].value = newValue;
}

function bestPrice(bestFunction, priceList, bestSoFar) {
    for (var index in priceList) {
        if (priceList.hasOwnProperty(index)) {
            bestSoFar = bestFunction(bestSoFar, priceList[index]);
        }
    }
    return bestSoFar;
}

function bestBuyPrice(event) {
    return bestPrice(Math.max, getPrices(event.buy), top.chart.getMinPrice());
}

function bestSellPrice(event) {
    return bestPrice(Math.min, getPrices(event.sell), top.chart.getMaxPrice());
}

function removeDeletionPrice(price) {
    var deleter = getDeleteOrdersInput(price);
    if (deleter !== null) {
        reloadSubSubframe('deletion');
    }
}

function getDeleteOrdersInput(price) {
    var subFrame = getNamedIFrame('traderDynamicFrame');
    var form = subFrame.document.getElementById('orders_table');
    if (form === null) {
        return null;
    }
    var inputs = form.getElementsByTagName('input');
    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if (input.name == 'deleteOrderPrice' && input.value == price) {
            return input;
        }
    }
    return null;
}
