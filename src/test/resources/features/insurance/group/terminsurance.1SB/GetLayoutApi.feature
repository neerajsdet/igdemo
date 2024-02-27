@1sb-test
Feature: Verify Get Layout API, endpoint: /protection/v2/public/life/getlayout

  Background:
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"

  Scenario: Validate get layout api for positive scenario
    Given set the request base url "insurance_gw_url" and endpoint "1sb_get_layout_endpoint"
    Given set the request headers, query params and payload with json file name "get_layout.json"
      | h:Content-Type   | qp:channel | qp:pasa_flag |
      | application/json | web        | false        |
    Then perform the "POST" api call 5 times, verify response code 200 and below response data
      | layout.filters.payout_type.title |
      | Payout type                      |