/*
Copyright 2008 Chris Hibbert.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
*/

/*globals: */

///////////// General support

function updateVisibility(newlyVisible, others) {
    var visible = document.getElementById(newlyVisible);
#console.debug("visible list is '" + visible + ";");
    if (visible != null) {
        visible.style.display = 'block';
    }
    var invisibles = others.split(",");
#console.debug("invisible list is '" + invisibles + ";");
    for (var i = 0; i < invisibles.length; i++) {
        if (invisibles.length == 0) { continue; }
        var invisible = document.getElementById(invisibles[i]);
        if (invisible != null && invisible != "") {
            invisible.style.display = 'none';
        }
    }
}

function copyRoleNode(nodeName) {
    var node = document.getElementById(nodeName);
    var parent = node.parentNode;
    var sibling = node.cloneNode(true);
    parent.appendChild(sibling);
    updateNameAndId(sibling, nodeName);
}

function successor(nodeName) {
    var len = nodeName.length;
    var lastChar = nodeName.charCodeAt(len - 1);
    var followingChar = String.fromCharCode(lastChar + 1);
    return nodeName.substring(len - 1,0) + followingChar;
}

function updateNameAndId(node, oldName) {
    var oldTail = oldName.split("-")[1];
    var newName = successor(oldName);
    node.id = newName;
    var newTail = newName.split("-")[1];
    var html = node.firstChild.innerHTML;
    node.firstChild.innerHTML = html.replace(new RegExp(oldTail, "g"), newTail);
}

function removeNode(nodeName) {
    var node = document.getElementById(nodeName);
    var parent = node.parentNode;
    parent.removeChild(node);
}
