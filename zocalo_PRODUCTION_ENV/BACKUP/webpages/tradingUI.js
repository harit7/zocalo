/*
Copyright 2008, 2009 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
*/

/*global channels */

var channels = [];

///////////// General support

function getNamedIFrame(name) {
    return getNamedSubframe(name, top);
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

function unsubscribeAll() {
    for (channel in channels) {
        dojox.cometd.unsubscribe(channel);
    }
}

///////////// Trading UI

function changeCellText(cellClass, newText) {
    var cell = document.getElementById(cellClass);
    var oldLabel = cell.innerHTML;
    cell.innerHTML = newText;
    return oldLabel;
}

function updateLabelsForBuy() {
	changeCellText('PriceGoes', "price goes<br> up to ...");
	changeCellText('SellUntil', "Buy Until");
	changeCellText('QuantLimit', "or I've<br>bought");
}

function updateLabelsForSell() {
	changeCellText('PriceGoes', "price goes<br> down to ...");
	changeCellText('SellUntil', "Sell Until");
    changeCellText('QuantLimit', "or I've<br>sold");
}

function highlight(label) {
  var tbl = document.getElementById("buysell");
  for (i = 0 ; i < tbl.rows.length ; i++) {
	var row = tbl.rows[i];
	if (row.id !== null && "" !== row.id) {
	  if (label == row.id || row.id == label) {
		row.className = "chosen";
	  } else {
		row.className = "";
	  }
	}
	var selectInput = document.getElementById("rowSelection");
	selectInput.value=label;
  }
}

function updateMMPrices(priceList) {
    for (var issue in priceList) {
        if (priceList.hasOwnProperty(issue) && issue !== "priceChange" && document.getElementById(issue + '-price') !== null) {
            fadeAndChangeCell(issue + '-price', priceList[issue]);
        }
    }
}

function fadeAndChangeCell(cellID, newText) {
    var flash = dojo.fadeOut({ node: cellID, duration: 200 });
    dojo.connect(flash,"onEnd",function() {
        changeCellText(cellID, newText);
        dojo.fadeIn({ node: cellID, duration: 1000 }).play();
    });
    flash.play();
}

function handleNewMarket(priceList) {
    addNewMarketLink(document.URL, priceList["priceChange"]);
}

function addNewMarketLink(curPage, claim) {
    var belowNavSpan = document.getElementById("belowNavButtons");
    if (belowNavSpan === null) { return; }

    var form = document.createElement("form");
    form.method = "post";
    form.action = curPage.split("?")[0] + "?claimName=" + encode(claim);
    var input = document.createElement("input");
    input.type = "submit";
    input.className = "smallFontButton";
    input.name = "action";
    input.value = claim.displayText;
    form.appendChild(input);
    belowNavSpan.appendChild(form);
    belowNavSpan.parentNode.align = "center";
    form.innerHTML = "<br>new Market: " + form.innerHTML;
}

function encode(component) {
    // see http://www.albionresearch.com/misc/urlencode.php
    return escape(component.replace(/ /g, "+"));
}
