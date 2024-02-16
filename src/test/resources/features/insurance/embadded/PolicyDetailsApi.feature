@shop-ins
Feature: Verify Policy Details API, endpoint: /protection/v1/public/policy/details

  Scenario: Validate policy details api - success
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "insurance_url" and endpoint "policy_details_api_endpoint"
    Given set the request headers and query params
      | h:sso_token | h:Content-Type   | qp:channel | qp:order_id  |
      | sso_token   | application/json | androidapp | 100076486209 |
    Then perform the "GET" api call, verify response code 200 and below response data
      | title              |
      | Manage My Policies |