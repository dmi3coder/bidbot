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
