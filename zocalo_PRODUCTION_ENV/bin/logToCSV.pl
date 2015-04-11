#!/usr/bin/env perl

#     Copyright 2008, 2009 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

package Zocalo;

use strict;
use lib '../etc';
use lib 'etc';
use ProcessLogs;

my $strict = $ENV{'STRICT_CHECKING'};
my @lines=<>;
my ($filename) = ($ARGV =~ /(.*).log/);

sub sortByID {
  my ($aID) = ($a =~ /.* - ([0-9]+)# .*/);
  my ($bID) = ($b =~ /.* - ([0-9]+)# .*/);
 $aID <=> $bID;
}

sub printAll {
    my($time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $clue, $sessionType, $market, $outcome) = @_;
  if ($sessionType =~ "EXP") {
    ProcessLogs::printExp($filename, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $clue, $outcome);
  } else {
    ProcessLogs::printPM($filename, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $market, $outcome);
  }
}

ProcessLogs::printTitles();

local *ProcessLogs::printAll =  'Zocalo::printAll';

foreach(sort sortByID @lines) {
    processLines();
}
