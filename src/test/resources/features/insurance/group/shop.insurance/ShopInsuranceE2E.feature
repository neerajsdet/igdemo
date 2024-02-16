@shop-ins @shop-ins-e2e
Feature: Verify shop insurance E2E flow up to policy details

  Scenario: Shop Insurance E2E flow verification
    Given generate the sso token with mobile "7777764102", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "insurance_bff_url" and endpoint "shop_ins_product_details_submit_endpoint"
    Given set the request headers, query params and payload with json file name "shop_product_details.json"
      | h:Content-Type   | h:sso_token | h:merchantid         | h:authorization                                                        | qp:product     | qp:journey | qp:state        |
      | application/json | sso_token   | SixLZX59901562375729 | Basic dW1wLXN0YWdpbmc6Y2FiOGQxOTQtNWYwNy00YzRlLWJmMGEtY2EzOGMxM2M1Zm1v | shop-insurance | group      | product-details |
    Then perform the "POST" api call 5 times and validate response code 201

    Given set the request base url "insurance_bff_url" and endpoint "shop_ins_proposal_submit_endpoint"
    Given set the request headers, query params and payload with json file name "shop_proposal.json"
      | h:Content-Type   | h:sso_token | h:merchantid         | h:authorization                                                        | qp:product     | qp:journey |
      | application/json | sso_token   | SixLZX59901562375729 | Basic dW1wLXN0YWdpbmc6Y2FiOGQxOTQtNWYwNy00YzRlLWJmMGEtY2EzOGMxM2M1Zm1v | shop-insurance | group      |
    Then perform the "POST" api call 5 times and validate response code 201 and get the value of "ORDER_ID,MID,native_withdraw_details.txnToken" from response and save in global data with key "order_id,mid,txn_token"

    Given set the request base url "pg_url" and endpoint "process_transaction_endpoint"
    Given set the request headers, query params and payload with json file name "process_trans.json"
      | h:Content-Type   | qp:mid | qp:orderId | head.txnToken | head.token | body.mid | body.orderId |
      | application/json | mid    | order_id   | txn_token     | txn_token  | mid      | order_id     |
    Then perform the "POST" api call, verify response code 200 and below response data
      | body.resultInfo.resultMsg |
      | Success                   |

    Given set the request base url "insurance_url" and endpoint "policy_details_api_endpoint"
    Given set the request headers and query params
      | h:Content-Type   | h:sso_token | qp:channel | qp:order_id |
      | application/json | sso_token   | androidapp | order_id    |
    Then perform the "GET" api call, verify response code 200 and below response data
      | title              | my_ins_details[0].plan_details.sum_insured |
      | Manage My Policies | 2500000                                    |

