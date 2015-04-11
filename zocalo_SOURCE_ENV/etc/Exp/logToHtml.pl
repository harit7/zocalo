#!/usr/bin/env perl

#     Copyright 2008, 2009 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

package Zocalo;

use strict;
use CGI qw(:standard);
use lib '../etc';
use ProcessLogs ('processLines', 'getRole', 'getBestBid', 'getBestAsk', 'getRound');

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

sub htmlPrintTitles {
  my ($sessionType) = @_;
  if ($sessionType =~ "EXP") {
    print "<tr><th>SessionID<th>Time<th>Event<th>Round<th>Actor<th>Action<th>Quant";
    print "<th>Price<th>Buyer<th>Seller<th>BestBid<th>BestAsk<th>Role<th>Actual";
    print "<th>Target<th>Outcome<th>Hint<th>Pos. Targets<th>Pos. Clues</tr>\n";
  } else {
    print "<tr><th>SessionID<th>Time<th>Event<th>Round<th>Actor<th>Action<th>Price",
          "<th>Buyer<th>Seller<th>BestBid<th>BestAsk<th>MM Price</tr>\n";
  }
}

sub htmlExpPrint {
    my($file, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $clue, $outcome) = @_;
    my $role = getRole($actor);
    my $round = ProcessLogs::getRound();
    my $actual = $ProcessLogs::actualValues[$round - 1];
    my $target = ProcessLogs::getTarget($actor, $round);
    my $hint = ProcessLogs::getHint($actor, $round);
    my $posTargets = 0 + $ProcessLogs::posTargets[$round - 1];
    my $posClues = 0 + $ProcessLogs::posClues[$round - 1];
    my $td = "&nbsp;<td>";
    my $bestBid = ProcessLogs::getBestBid();
    my $bestAsk = ProcessLogs::getBestAsk();

    print "<tr><td>$file$td$time$td$event$td$round$td$actor$td$action$td$quant$td$price$td$buyer$td";
    print "$seller$td$bestBid$td$bestAsk$td$role$td$actual$td$target$td$outcome$td$hint$td";
    print "$posTargets$td$posClues</tr>\n";
}

sub htmlPMPrint {
    my($file, $time, $event, $actor, $action, $quant, $price, $buyer, $seller, $type, $state, $market, $outcome, $goalAction, $goalIssue, $costLimit, $priceTarget, $totalCost) = @_;
    my $td =~ "&nbsp;<td>";
    my $bestBid = $ProcessLogs::bestBid;
    my $bestAsk = $ProcessLogs::bestAsk;
    my $mmPrice = $ProcessLogs::mmPrice;

    print "<tr><td>$file$td$time$td$event$td$actor$td$action$td$quant$td$price$td$buyer$td$seller";
    print "$td$bestBid$td$bestAsk$td$mmPrice</tr>\n";
}

local *ProcessLogs::printAll =  'Zocalo::printAll';
local *ProcessLogs::printTitles = 'Zocalo::htmlPrintTitles';
local *ProcessLogs::printExp =  'Zocalo::htmlExpPrint';
local *ProcessLogs::printPM = 'Zocalo::htmlPMPrint';

ProcessLogs::cgiHeaders($filename);

foreach(@lines) {
    processLines();
}

ProcessLogs::cgiFooter();
close (LOGFILE);

