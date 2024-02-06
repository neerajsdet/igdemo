@shop-insurance
Feature: Verify User Details API, endpoint: /v2/public/standalone/getuserdetails

  Scenario: Validate user details api - success
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "insurance_url" and endpoint "user_details_api_endpoint"
    Given set the request headers, query params and payload with json file name "user_details.json"
      | h:sso_token | h:Content-Type   | qp:insurance_type | qp:channel |
      | sso_token   | application/json | 144               | consumer   |
    Then perform the "POST" api call, verify response code 200 and below response data
      | userDetails[0].label |
      | Confirm your details |