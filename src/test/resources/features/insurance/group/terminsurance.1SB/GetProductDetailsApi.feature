@1sb
Feature: Verify Get Product Details API, endpoint: /protection/v1/public/standalone/getproductdetails

  Background:
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"

  Scenario: Validate get product details api for positive scenario
    Given set the request base url "insurance_gw_url" and endpoint "1sb_get_product_details_endpoint"
    Given set the request headers, query params
      | h:Content-Type   | qp:channel | qp:pasa_flag | qp:insurance_type |
      | application/json | web        | false        | 15                |
    Then perform the "GET" api call 5 times, verify response code 200 and below response data
      | captions.header |
      | Term Insurance  |

