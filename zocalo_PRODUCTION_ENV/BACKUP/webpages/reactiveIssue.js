// Copyright 2009 Chris Hibbert.  All rights reserved.
//
// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/*global */

// If the price needs to be updated and there's currently no focus or no target
// price, we animate the change and leave latest as the definitive value.
// If we have the focus, we animate the change and leave both values in the form
// until blur.  The reference fades when focus is lost.  If there's a conflict
// in pricing, the new value glows.

var reactiveIssueRow = function (name, label, market, noWaiting) {
    var that = {};
    var rowName = name;
    var rowLabel = label;
    var latestPrice;        // latest market price for each outcome
    var referencePrice;     // market price for focal outcome when editing started
    var targetPrice;        // user's desired trading price
    var hasFocus = false;

    var T = 'Target';
    var R = 'Reference';
    var L = 'Latest';

///////////// getters and setters

    that.getLatestPrice = function() { return latestPrice; };
    that.getTargetPrice = function() { return targetPrice; };
    that.getReferencePrice = function() {
        if (referencePrice === 0) {
            return that.getLatestPrice();
        }
        return referencePrice; 
    };
    that.getLabel = function() { return rowLabel; };
    function setLatestPrice(price) { latestPrice = 1 * price; }
    function setReferencePrice(price) { referencePrice = 1 * price; }
    function setTargetPrice(price) { targetPrice = 1 * price; }

///////////// DOM access

    that.changeTarget = function (newValue) {
        changeInputField(rowName, T, newValue);
        setTargetPrice(newValue);
    };

    that.changeReference = function (newValue) {
        setSpanContents(rowName, R, newValue);
        setReferencePrice(newValue);
    };

    that.changeLatest = function (newValue) {
        setSpanContents(rowName, L, newValue);
        setLatestPrice(newValue);
    };

    that.getTargetField = function () {
        return getFieldValue(rowName, T);
    };

    that.getLatestField = function () {
        return getFieldValue(rowName, L);
    };

    that.getReferenceField = function () {
        return getFieldValue(rowName, R);
    };

    that.getLatestValue = function () {
        var wholeSpan = getSpanContents(rowName, L);
        if (wholeSpan === null) { return 0; }

        var digits = wholeSpan.substring(0,wholeSpan.lastIndexOf("¢"));
        return 1 * digits;
    };

    that.setChosen = function (chosen) {
        var row = document.getElementById(rowName);
        if (chosen) {
            if (that.getReferencePrice() < that.getTargetField()) {
                row.className = "chosen-buy";
            } else {
                row.className = "chosen-sell";
            }
        } else {
            row.className = "";
        }
    };

///////////// event handlers

    that.handleChange = function () {
        targetPrice = 1 * this.getTargetField();
        market.highlight(rowName);
    };

    that.newPrice = function(price) {
        if (hasFocus) {
            updateFocalPrice(price);
        } else {
            updateBlurredPrice(price);
        }
    };

    that.handleFocus = function() {
        hasFocus = true;
    };

    that.handleBlur = function() {
        hasFocus = false;
        var latest = that.getLatestPrice();
        unGlowNewFadeOld();
    };

    that.isLocked = function() {
        return hasFocus;
    };

    that.toString = function() {
        return rowName + " (" + rowLabel + ") " + (hasFocus? " ! " : "") + ": " + latestPrice + ", " +  referencePrice + ", " +  targetPrice;
    };

///////////// display updates

    function fadeOld() {
        var l = late(rowName);
        unglow(l);
        var r = refer(rowName);
        makeVisibleNow(r);
        fade(r);
    }

    function glowNewFadeOld() {
        makeVisible(rowName);
        glow(late(rowName));
        var r = refer(rowName);
        makeVisibleNow(r);
        fade(r);
    }

    function unGlowNewFadeOld() {
        unglowSlowly(late(rowName));
        fade(refer(rowName));
    }

    function fade(nodeName) {
        if (noWaiting) {
            document.getElementById(nodeName).style.opacity = 0;
        } else {
            dojo.fadeOut({node: nodeName, duration: 1000}).play();
        }
    }

    function unfade(nodeName) {
        if (noWaiting) {
            makeVisibleNow(nodeName);
        } else {
            var quickOut = dojo.fadeOut({ node: nodeName, duration: 200});
            var fadeIn = dojo.fadeIn({ node: nodeName, duration: 1000 });
            dojo.fx.chain([quickOut,fadeIn]).play();
        }
    }

    function makeVisibleNow(nodeName) {
        document.getElementById(nodeName).style.opacity = 1;
    }

    function makeVisible(nodeName) {
        if (noWaiting) {
            makeVisibleNow(nodeName);
        } else {
            dojo.fadeIn({node: nodeName, duration: 200}).play();
        }
    }

    function flashLatestPrice(newPrice) {
        if (noWaiting) {
            that.changeLatest(newPrice);
            makeVisibleNow(late(rowName));
        } else {
            var flash = dojo.fadeOut({ node: late(rowName), duration: 200 });
            dojo.connect(flash,"onEnd",function(){
                that.changeLatest(newPrice);
                dojo.fadeIn({ node: late(rowName), duration: 1000 }).play();
            });
            flash.play();
        }
    }

    function glowNewStrikeOld() {
        glow(late(rowName));
        strike(refer(rowName));
        unfade(refer(rowName));
    }

    // Are the reference price and the latest price on opposite sides of the target price?
    // That would mean that the trade would surprise the user if executed now.
    function surprisingPrices() {
        if (that.getTargetField() === null) {
            return false;
        }

        if (targetPrice >= referencePrice && targetPrice >= latestPrice) {
            return false;
        } else if (targetPrice <= referencePrice && targetPrice <= latestPrice) {
            return false;
        } else {
            return true;
        }
    }

///////////// support functions

    function glow(domID) {
        var node = document.getElementById(domID);
        if (node === null) { return; }

        makeVisibleNow(domID);
        node.className = 'glow';
    }

    function unglow(domID) {
        var node = document.getElementById(domID);
        if (node === null) { return; }

        makeVisibleNow(domID);
        node.className = '';
    }

    function unglowSlowly(domID) {
        var node = document.getElementById(domID);
        if (node === null) { return; }

        if (node.className === "glow") {
            // dojo's removeClass is supposed to animate the transition, but it doesn't seem to like the "glow" class
//            dojox.fx.removeClass(node, "glow", { delay: 500 }).play();
            node.className = "";
        }
    }

    function strike(domID) {
        var node = document.getElementById(domID);
        if (node === null) { return; }

        makeVisibleNow(domID);
        node.className = 'strike';
    }

    function refer(rowName) {
        return rowName + R;
    }

    function late(rowName) {
        return rowName + L;
    }

    function updateBlurredPrice(price) {
        if (that.getTargetPrice() === 0) {
            flashLatestPrice(price);
        } else {
            that.changeLatest(price);
            if (surprisingPrices()) {
                glowNewFadeOld();
            } else {
                that.changeReference(price);
                fadeOld();
            }
        }
        that.changeReference(price);
    }

    // the focus is in the field.  If exogenous price changes are benign, we animate them.
    // If they are surprising, we leave visible indications, but require confirmation before submitting. 
    function updateFocalPrice(price) {
        if (that.getLatestPrice() === price) {
            return;
        }
        that.changeLatest(price);
        if (that.getTargetPrice() !== "" && surprisingPrices()) {
            glowNewStrikeOld();
        } else {
            that.changeReference(price);
            glowNewFadeOld();
        }
    }

    function changeInputField(rowName, fieldName, newValue) {
        var inputField = getInputField(rowName, fieldName);
        inputField.value = newValue;
    }

    function getFieldValue(rowName, fieldName) {
        var inputField = getInputField(rowName, fieldName);
        if (inputField === null) { return null; }

        return inputField.value;
    }

    function getSpan(rowName, fieldName) {
        return document.getElementById(rowName + fieldName);
    }

    function getSpanContents(rowName, fieldName) {
        var row = getSpan(rowName, fieldName);
        if (row === null) { return null; }
        return row.innerHTML;
    }

    function setSpanContents(rowName, fieldName, value) {
        var row = getSpan(rowName, fieldName);
        if (row === null) { return; }
        row.innerHTML = "" + value + "¢";
    }

    function getInputField(rowName, fieldName) {
        var row = document.getElementById(rowName);
        if (row === null) { return null; }

        var inputs = row.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].name === rowName + fieldName) {
                return inputs[i];
            }
        }
        return null;
    }

    ///////////// initialization

    function getChosen(rowName) {
        var node = document.getElementById(rowName);
        return node.className.substring(0,7) === 'chosen-';
    }

    setLatestPrice(that.getLatestValue());
    setReferencePrice(that.getLatestPrice());
    setTargetPrice(that.getTargetField());

    return that;
};

var reactiveMarket = function(namesAndLabels, noWaiting) {
    var that = {};
    var rows = {};
    var labels = {};

///////////////////////////

    that.highlight = function(rowName) {
        for (var i = 0; i < namesAndLabels.length; i++) {
            var name = namesAndLabels[i][0];
            rows[name].setChosen(name === rowName);
        }

        var selectRowInput = document.getElementById("rowSelection");
        selectRowInput.value = rowName;
        var selectClaimInput = document.getElementById("claimSelection");
        selectClaimInput.value = that.row(rowName).getLabel();
    };

    that.handlePriceUpdate = function (newPricesEvent) {
        for (var i = 0; i < namesAndLabels.length; i++) {
            var name = namesAndLabels[i][0];
            var label = namesAndLabels[i][1];
            var newValue = newPricesEvent[label];
            rows[name].newPrice(newValue);
        }
    };

    that.toString = function() {
        return "reactive market with " + namesAndLabels.length + " rows.";
    };

    that.row = function(rowName) {
        return rows[rowName];
    };

    that.label = function(rowlabel) {
        return rows[rowlabel];
    };

/////////////////////////// Initiialization

    that.initializeRows = function(rowNamesAndLabels) {
        for (var i = 0; i < rowNamesAndLabels.length; i++) {
            var name = rowNamesAndLabels[i][0];
            var label = rowNamesAndLabels[i][1];
            var row = reactiveIssueRow(name, label, that, noWaiting);
            rows[name] = row;
            labels[label] = row;
        }
    };

    that.initializeRows(namesAndLabels);

    return that;
};
