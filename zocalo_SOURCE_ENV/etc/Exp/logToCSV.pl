#!/usr/bin/env perl

#     Copyright 2008, 2009 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

package Zocalo;

use strict;
use CGI qw(:standard);
use lib '../etc';
use ProcessLogs;

my $query = new CGI;
my ($filename) = $query->keywords;
open(LOGFILE, "../$filename");
my @lines=<LOGFILE>;

sub sortByID {
  my ($aID) = ($a =~ /.* - ([0-9]+)# .*/);
  my ($bID) = ($b =~ /.* - ([0-9]+)# .*/);
 $aID <=> $bID;
}

sub printAll {
    my($time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $clue, $sessionType, $market, $outcome, $goalAction, $goalIssue, $costLimit, $priceTarget, $totalCost) = @_;
  if ($sessionType =~ "EXP") {
    ProcessLogs::printExp($filename, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $clue, $outcome);
  } else {
    ProcessLogs::printPM($filename, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $market, $outcome, $goalAction, $goalIssue, $costLimit, $priceTarget, $totalCost);
  }
}

local *ProcessLogs::printAll =  'Zocalo::printAll';

print "Content-type: text/html\n\n";

foreach(@lines) {
    processLines();
}

close (LOGFILE);

