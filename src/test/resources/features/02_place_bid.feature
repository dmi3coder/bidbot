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