Feature: Cucumber works
  Scenario: Test runs
  When test runs
  Then this case completes

Feature: Initialize bidder
  A product x QU (quantity units) will be auctioned under 2 parties
  The parties have each y MU (monetary units) for auction
  Scenario Template: Bidder initializes with initial values
    Given dummy Bidder created
    When call init with <qu> quantity and <cash> cash
    Then dummy Bidder should have <qu> quantity and <cash> cash

    Examples:
      | qu | cash |
      | 10 | 1000 |
      | 10 | 2000 |

Feature: Place bid
  They offer then simultaneously an arbitrary number of its MU
  on the first 2 QU of the product.
  Scenario Template:
    Given dummy Bidder created
    And call init with <qu> quantity and <cash> cash
    When Auction asks to bid
    Then bid <bid_amount> cash
    And Auction received <bid_amount> cash

    Examples:
      | qu | cash | bid_amount |
      | 10 | 1000 | 100        |
      | 10 | 2000 | 200        |

Feature: Handle results
  After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU;
  if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated.
  A bid of 0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned.
  Background:
    Given dummy Bidder created
    And call init with 10 quantity and 1000 cash

  Scenario: Handle incoming bids info from both bidders
    After that, the bids will be visible to both.
    When Bidder received 200 from Bidder and 300 from Opponent
    Then Bidder should store 200 from Bidder and 300 from Opponent into results history
    And Bidder should withdraw 200 from available cash

  Scenario Template: Handle bid situation
    The 2 QU of the product is awarded to who has offered the most MU;
    if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated.
    A bid of 0 MU is allowed.
    When Bidder received <own_bid_amount> from Bidder and <opponent_bid_amount> from Opponent
    Then Bidder should store <own_bid_amount> from Bidder and <opponent_bid_amount> from Opponent into results history
    And Bidder should add <bidder_qu_amount> to Bidder and <opponent_qu_amount> to Opponent

    Examples:
      | own_bid_amount | opponent_bid_amount | bidder_qu_amount | opponent_qu_amount |
      | 300            | 200                 | 2                | 0                  |
      | 200            | 300                 | 0                | 2                  |
      | 300            | 300                 | 1                | 1                  |
      | 0              | 300                 | 0                | 2                  |
      | 300            | 0                   | 0                | 0                  |
      | 0              | 0                   | 0                | 0                  |

  Scenario Template: Handle complete auction process
    Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned.
    When Bidder received <own_bid_amount> from Bidder and <opponent_bid_amount> from Opponent 5 times
    Then Bidder should have <bidder_final_cash_amount> cash and <bidder_final_qu_amount> quantity

    Examples:
      | own_bid_amount | opponent_bid_amount | bidder_final_cash_amount | bidder_final_qu_amount |
      | 200            | 300                 | 0                        | 0                      |
      | 200            | 100                 | 0                        | 10                     |
      | 200            | 200                 | 0                        | 5                      |
      | 100            | 10                  | 500                      | 10                     |
      | 100            | 100                 | 500                      | 5                      |
      | 0              | 200                 | 1000                     | 0                      |
      | 0              | 0                   | 1000                     | 5                      |
