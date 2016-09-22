TDD training app in e-commerce domain
=====================================

Backlog:
--------

  * Update quantity of a product already in cart (ie. add one `Cup`, then add another). 
  * Add two different products to cart (ie. add one `Cup` and then add a `Plate`).
  * `BAD_REQUEST` when trying to add not existing product into `Cart`
  * `BAD_REQUEST` when trying to add a product with negative quantity
  * Calculate total for cart with many products in different quantities.
  * Cart contains a fixed shipping cost. Total includes shipping now (ie. `GET /user/6/cart` returns `$.shipping = 15`).
  * Offer free shipping if cart value bigger than $100.
  * Store users shipping address (using bottom-up approach)
  * Place an order (ie. `POST /user/6/cart/purchase` creates an `Order` with `OrderItems`)
  * Create search engine to find products by name, category and availabilityDate 
