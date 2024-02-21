@test
Feature: Verify Create Quote API, endpoint: /v1/public/embedded/createQuote

  Scenario Outline: Validate create quote api - success
    Given set the request base url "insurance_url" and endpoint "create_quote_api_endpoint"
    Given set the request headers, query params and payload with json file name "create_quote.json"
      | h:Content-Type   | int:lending_pid | int:loan_info.tenure |
      | application/json | <lending_pid>   | <tenure>             |
    Then perform the "POST" api call 1 times, verify response code 200 and below response data
      | checkout_details.product_id | checkout_details.price |
      | 12323                       | 28251                  |

    Examples:
      | lending_pid | tenure |
      | 100         | 20     |