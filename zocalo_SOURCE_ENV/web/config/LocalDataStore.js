// Copyright 2009 Chris Hibbert.  All rights reserved.
//
// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

// a pretend dojo.data data store that keeps the info in local variables.
// It's intended to be used for a dijit.form.FilteringSelect in Zocalo's
//  configEditing tools.  untested, unfinished.
// It's "pretend" in that it doesn't implement the full dojo.data.Read API
// but it should be sufficient for FilteringSelect anyway.
var LocalDataStore = function (initialValues) {
    var that = {};
    var data = initialValues.copy();
    var searchAttr = 'name';

    that.getValue = function(item, /* ignored */ attribute, defaultValue) {
        var value = data[searchAttr];
        if (value === null) {
            return defaultValue;
        } else {
            return value;
        }
    };

   that.isItemLoaded = function(ignored) {
        return true;
   };

//    that.fetch = function(ignore) {
//        // TODO: Is this needed for FilteringSelect?
//        return;
//    };

    that.close = function () {
        return;
    };

    that.getIdentity = function (item) {
        return data.[item].value;
    };

    that.fetchItemByIdentity = function (args) {
        var item = data[args.identity];
        args.onItem(item);
    };

    that.loadItem = function(args) {
        args.onItem(data[args.item]);
    }

    return that;
}