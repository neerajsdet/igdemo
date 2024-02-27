@cyber-fraud-embedded-E2E
Feature: Verify Insurance Embedded BE order creation E2E flow up to policy details

  Scenario Outline: Embedded Insurance E2E flow verification
    Given generate the sso token with mobile "7055585511", password "paytm@123" and save in global data with key "sso_token"

#    Given set the request base url "insurance_url" and endpoint "proposal_details_api_endpoint"
#    Given set the request headers, query params and payload with json file name "getproposaldetails.json"
#      | h:Content-Type   | h:sso_token | qp:insurance_type |
#      | application/json | sso_token   | 165               |
#    Then perform the "POST" api call 1 times and validate response code 200

    Given set the request base url "insurance_gw_url" and endpoint "verify_insurance_endpoint"
    Given set the request headers, query params and payload with json file name "oms_verify.json"
      | h:Content-Type   | h:sso_token | cart_items[0].product_id | cart_items[0].configuration.insurance_type |
      | application/json | sso_token   | <pid>                    | <ins_type_id>                              |
    Then perform the "POST" api call 1 times and validate response code 200

    Given set the request base url "insurance_gw_url" and endpoint "checkout_insurance_endpoint"
    Given set the request headers, query params and payload with json file name "oms_verify.json"
      | h:Content-Type   | h:sso_token | cart_items[0].product_id | qp:client | qp:native_withdraw | cart_items[0].configuration.insurance_type |
      | application/json | sso_token   | <pid>                    | iosapp    | 1                  | <ins_type_id>                              |
    Then perform the "POST" api call 1 times and validate response code 200 and get the value of "ORDER_ID,MID,native_withdraw_details.txnToken,TXN_AMOUNT" from response and save in global data with key "order_id,mid,txn_token,txn_amount"

    Given set the request base url "pg_url" and endpoint "process_transaction_endpoint"
    Given set the request headers, query params and payload with json file name "ptc.json"
      | h:Content-Type   | qp:mid | qp:orderId | body.mid | body.orderId | head.txnToken | body.txnToken |
      | application/json | mid    | order_id   | mid      | order_id     | txn_token     | txn_token     |
    Then perform the "POST" api call 1 times, verify response code 200 and below response data
      | body.resultInfo.resultMsg |
      | Success                   |

    Given set the request base url "insurance_url" and endpoint "policy_details_api_endpoint"
    Given set the request headers, query params
      | h:sso_token | qp:order_id |
      | sso_token   | order_id    |
    Then perform the "GET" api call 1 times and validate response code 200 and get the value of "my_ins_details[0].premium_details.item_id" from response and save in global data with key "cart_item_id"

    Given set the request base url "insurance_url" and endpoint "order_notify_api_endpoint"
    Given set the request headers, query params and payload with json file name "ordernotify.json"
      | h:Content-Type   | id       | items[0].id  | items[0].order_id | items[0].price | items[0].product_id |
      | application/json | order_id | cart_item_id | order_id          | txn_amount     | <pid>               |
    Then perform the "POST" api call 1 times and validate response code 200

    Examples:
      | scenario           | pid        | ins_type_id |
      | test for product 1 | 1235419187 | 165         |
      | test for product 2 | 1235419188 | 165         |
      | test for product 3 | 1235419186 | 165         |
      | test for product 4 | 1235419189 | 165         |