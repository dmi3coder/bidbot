Feature: Cucumber works
  Scenario: Test runs
  When test runs
  Then this case completes
Feature: Initialize bidder
  A product x QU (quantity units) will be auctioned under 2 parties
  The parties have each y MU (monetary units) for auction
  Scenario Outline: Bidder initializes with initial values
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
  Scenario Outline:
    Given dummy Bidder created
    And call init with <qu> quantity and <cash> cash
    When Auction asks to bid
    Then bid <bid_amount> cash
    And Bidder should
    And Auction received <bid_amount> cash

    Examples:
      | qu | cash | bid_amount |
      | 10 | 1000 | 100        |
      | 10 | 2000 | 200        |

