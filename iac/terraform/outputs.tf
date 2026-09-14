output "url" {
  description = "the URL of the deployed service"
  value       = google_cloud_run_v2_service.service.uri
}
