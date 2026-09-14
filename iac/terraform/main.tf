resource "google_cloud_run_v2_service" "service" {
  name                = "cloudrun-service-terraform"
  location            = var.region
  deletion_protection = false
  ingress             = "INGRESS_TRAFFIC_ALL"

  template {
    containers {
      image = var.image_name
      resources {
        limits = {
          memory = var.memory
          cpu    = var.cpu
        }
      }
      ports {
        container_port = var.container_port
      }
    }
  }
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_v2_service_iam_policy" "service-noauth" {
  location = google_cloud_run_v2_service.service.location
  project  = google_cloud_run_v2_service.service.project
  name     = google_cloud_run_v2_service.service.name

  policy_data = data.google_iam_policy.noauth.policy_data
}

# terraform init
# terraform plan
# terraform apply
# terraform destroy
