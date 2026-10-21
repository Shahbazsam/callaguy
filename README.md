# Call A Guy – Backend (Ktor + PostgreSQL)

Backend for the **Call A Guy** system (Customer, Professional, Admin apps).  
REST APIs for auth, catalog (services/subservices), service requests, professional approval, support tickets + chat, media uploads, and (mock) payments.  
Built with **Ktor**, **PostgreSQL**, **Exposed**, **JWT (HMAC256)**, and **Flyway** migrations.

---

## 🧩 Features

- **JWT Auth + Roles**: `admin`, `customer`, `professional` (role checks at route level)
- **Catalog**: Services & SubServices
- **Service Requests**: create (customer), list, accept/complete (professional)
- **Professional Approval**: admin review, approve/reject
- **Support Desk**: tickets + threaded messages (customer ↔ admin)
- **Media Uploads**: profile pictures (customer/professional)
- **Payments**: initial flow (mock implementation)
- **Migrations**: **Flyway** on startup
- **Docs**: **Swagger/OpenAPI** (generated)

---

## 🛠 Tech Stack

• Kotlin
• Ktor
• PostgreSQL 
• Exposed ORM 
• Flyway 
• Kotlinx Serialization 
• JWT (HMAC256) 
• Gradle (Kotlin DSL)

---

## 🏗 Architecture (high-level)


Ktor Routing
├─ Auth (admin/customer/professional)
├─ Catalog (services, subservices)
├─ ServiceRequests (customer/professional)
├─ Admin (approvals)
├─ Support (tickets, messages)
├─ Media (profile uploads)
└─ Payment
↓
Use Cases / Services
↓
Repositories
↓
Exposed ORM → PostgreSQL


---

## 📚 API (exact routes)

> Base prefix examples shown without `/api/v1` for brevity. Add your versioning prefix if configured.

### Auth
- **Admin**
  - `POST /admin_auth/register`
  - `POST /admin_auth/login` → `AuthResponse { token }`
- **Customer**
  - `POST /customer_auth/register`
  - `POST /customer_auth/login` → `AuthResponse { token }`
- **Professional**
  - `POST /professional_auth/register`
  - `POST /professional_auth/login` → `AuthResponse { token }`
  - `GET /professional_auth/is_approved` *(auth: professional)* → `Boolean`

### Admin – Professional Approval *(auth: admin)*
- `GET  /admin/pending_approval` → list pending professionals
- `POST /admin/approve` (body: `{ id }`) → “Approved Successfully”
- `DELETE /admin/reject/{id}` → “Professional Rejected Successfully”

### Catalog *(auth: any)*
- `GET /services` → all services
- `GET /services/{id}` → subservices by service id

### Customer – Service Requests *(auth: customer)*
- `POST /customer_service_request/create` (body: `CreateServiceRequestDto`)
- `GET  /customer_service_request` → own requests
- `GET  /customer_service_request/{id}` → details

### Professional – Service Requests *(auth: professional)*
- `GET  /professional_service_request` → eligible requests
- `GET  /professional_service_request/my_orders` → accepted by me
- `POST /professional_service_request/accept` (body: `{ requestId }`)
- `POST /professional_service_request/update_status` (body: `{ requestId, newStatus }`)
- `GET  /professional_service_request/{id}` → details

### Support Tickets *(auth: role as noted)*
- **Tickets**
  - `POST /support_ticket/create` *(customer)* → create ticket
  - `GET  /support_ticket/customer_tickets` *(customer)* → my tickets
  - `POST /support_ticket/update_status` *(admin)* → resolve/close
  - `GET  /support_ticket/all_tickets` *(admin)* → all tickets
- **Messages**
  - `POST /support_messages/create` *(customer/admin)* (body: `SupportMessageRequestDto`)
  - `GET  /support_messages/{id}` → messages by ticket id

### Media – Profile Pictures
- **Customer** *(auth: customer)*
  - `POST /customer_profile` → multipart `image` upload → `{ imageUrl }`
  - `GET  /customer_profile/profile_info`
- **Professional** *(auth: professional)*
  - `POST /professional_profile_picture` → multipart `image` upload → `{ imageUrl }`
  - `GET  /professional_profile_picture/profile_info`

### Payment *(auth: customer)*
- `POST /payment/make_payment` (body: `InitialPaymentRequestDto`)
- `GET  /payment/payment_information/{id}`
- `PUT  /payment/{id}/status`

---

## 🔐 Auth Notes

- **JWT** includes `userId`, `role`, `exp`.
- Role validation is enforced at route level.
- Consistent error handling via custom exceptions.

---

## 🗄 Database & Migrations (Flyway)
PostgreSQL required.

Flyway applies migrations on startup from resources/db/migration (e.g., V1__init.sql, V2__add_tables.sql).

Configure connection via env or application.conf.

---

📌 Future Plans

Push notifications (server-driven)

CI/CD pipeline & tests

Metrics/monitoring


## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```


👤 Author
Mohammad Shahbaz — GitHub: Shahbazsam

