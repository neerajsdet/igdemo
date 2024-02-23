@1sb-test
Feature: Verify 1SB Get User Detail API, endpoint: /protection/v2/public/life/getuserdetails

  Background:
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"

  Scenario: Validate 1SB get user details api
    Given set the request base url "insurance_gw_url" and endpoint "1sb_get_layout_endpoint"
    Given set the request headers, query params and payload with json file name "get_layout.json"
      | h:Content-Type   | qp:channel | qp:pasa_flag |
      | application/json | web        | false        |
    Then perform the "POST" api call 5 times and validate response code 200 and get the value of "meta_data.request_id" from response and save in global data with key "request_id"

    Given set the request base url "insurance_gw_url" and endpoint "1sb_get_user_details_endpoint"
    Given set the request headers, query params and payload with json file name "get_user_details.json"
      | h:Content-Type   | meta_data.request_id |
      | application/json | request_id           |
    Then perform the "POST" api call 5 times, verify response code 200 and below response data
      | userDetails[0].label     |
      | Policyholder information |