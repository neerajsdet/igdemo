@cyber-fraud-embedded-E2E
Feature: Verify Insurance Embedded BE order creation E2E flow up to policy details

  Scenario: Embedded Insurance E2E flow verification
    Given generate the sso token with mobile "7055585511", password "paytm@123" and save in global data with key "sso_token"

#    Given set the request base url "insurance_url" and endpoint "proposal_details_api_endpoint"
#    Given set the request headers, query params and payload with json file name "getproposaldetails.json"
#      | h:Content-Type   | h:sso_token | qp:insurance_type |
#      | application/json | sso_token   | 165               |
#    Then perform the "POST" api call 1 times and validate response code 200

    Given set the request base url "insurance_gw_url" and endpoint "verify_insurance_endpoint"
    Given set the request headers, query params and payload with json file name "oms_verify.json"
      | h:Content-Type   | h:sso_token | cart_items[0].product_id | cart_items[0].configuration.insurance_type |
      | application/json | sso_token   | 1235419188               | 165                                        |
    Then perform the "POST" api call 1 times and validate response code 200

    Given set the request base url "insurance_gw_url" and endpoint "checkout_insurance_endpoint"
    Given set the request headers, query params and payload with json file name "oms_verify.json"
      | h:Content-Type   | h:sso_token | cart_items[0].product_id | qp:client | qp:native_withdraw |
      | application/json | sso_token   | 1235419188 | iosapp    | 1                  |
    Then perform the "POST" api call 1 times and validate response code 200 and get the value of "ORDER_ID,MID,native_withdraw_details.txnToken" from response and save in global data with key "order_id,mid,txn_token"

    Given set the request base url "pg_url" and endpoint "process_transaction_endpoint"
    Given set the request headers, query params and payload with json file name "process_trans.json"
      | h:Content-Type   | qp:mid | qp:orderId | head.txnToken | head.token | body.mid | body.orderId |
      | application/json | mid    | order_id   | txn_token     | txn_token  | mid      | order_id     |
    Then perform the "POST" api call 1 times, verify response code 200 and below response data
      | body.resultInfo.resultMsg |
      | Success                   |

    Given set the request base url "insurance_url" and endpoint "policy_details_api_endpoint"
    Given set the request headers, query params
      | h:Content-Type   | h:sso_token | qp:channel | qp:order_id |
      | application/json | sso_token   | androidapp | order_id    |
    Then perform the "GET" api call 1 times and validate response code 200

