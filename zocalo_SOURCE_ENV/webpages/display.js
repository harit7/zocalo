/*
 Copyright 2009 Chris Hibbert.  All rights reserved.
 Copyright 2006 CommerceNet Consortium, LLC.  All rights reserved.

This software is published under the terms of the MIT license, a copy
of which has been included with this distribution in the LICENSE file.
*/

/*globals: */

///////////// General support

function toggleVisibility(id) {
    var el = document.getElementById(id);
    if (el.style.display === 'none') {
        el.style.display = 'block';
    } else {
        el.style.display = 'none';
    }
}

function refreshImage(image) {
    src = image.src;
    var imageSrcSplit = src.split("?");
    image.src = imageSrcSplit[0] + "?" + Math.random();
}
