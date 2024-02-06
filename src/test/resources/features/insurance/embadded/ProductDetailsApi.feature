@shop-insurance
Feature: Verify Product Details API, endpoint: /v2/public/standalone/getproductdetails

  Scenario: Validate product details api - success
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "insurance_url" and endpoint "product_details_api_endpoint"
    Given set the request headers and query params
      | h:X-USER-SSO-TOKEN | h:Content-Type   | qp:insurance_type | qp:channel |
      | sso_token          | application/json | 144               | android    |
    Then perform the "GET" api call, verify response code 200 and below response data
      | title          |
      | Shop Insurance |