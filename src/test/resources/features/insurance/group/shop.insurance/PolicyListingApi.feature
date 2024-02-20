@shop-ins
Feature: Validate Policy Listing API, endpoint: /v1/public/policy/listing
  
  
  Scenario: Validate Policy Listing API with valid data
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "insurance_url" and endpoint "shop_ins_policy_listing_endpoint"
    When set the request headers, query params
      | h:Content-Type   | h:sso_token |
      | application/json | sso_token   |
    Then perform the "GET" api call 2 times, verify response code 200 and below response data
      | title              | message |
      | Manage My Policies | ok      |