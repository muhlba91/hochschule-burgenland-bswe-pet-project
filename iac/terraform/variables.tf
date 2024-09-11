variable "region" {
  type        = string
  description = "the Google Cloud region"
  default     = "europe-west4"
}

variable "image_name" {
  type        = string
  description = "the image to deploy"
  default     = "gcr.io/cloudrun/hello"
}

variable "memory" {
  type        = string
  description = "the memory limit"
  default     = "512Mi"
}

variable "cpu" {
  type        = string
  description = "the CPU limit"
  default     = "1"
}

variable "container_port" {
  type        = number
  description = "the port the container listens on"
  default     = 8080
}
