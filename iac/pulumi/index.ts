import * as pulumi from "@pulumi/pulumi";
import * as gcp from "@pulumi/gcp";

/*
// commands to set the configuration details
pulumi config set region europe-west4
pulumi config set imageName gcr.io/cloudrun/hello
pulumi config set containerPort 8080
pulumi config set cpu 1
pulumi config set memory 512Mi
pulumi config set concurrency 80
 */

// container configuration
const config = new pulumi.Config();
const region = config.require("region");
const imageName = config.require("imageName");
const containerPort = config.requireNumber("containerPort");
const cpu = config.requireNumber("cpu");
const memory = config.require("memory");
const concurrency = config.requireNumber("concurrency");

// create a cloud run service
const service = new gcp.cloudrun.Service("service", {
    location: region,
    template: {
        spec: {
            containers: [
                {
                    image: imageName,
                    resources: {
                        limits: {
                            memory: memory,
                            cpu: cpu.toString(),
                        },
                    },
                    ports: [
                        {
                            containerPort: containerPort,
                        },
                    ],
                }
            ],
            containerConcurrency: concurrency,
        },
    },
});
new gcp.cloudrun.IamMember("service-noauth", {
    service: service.name,
    role: "roles/run.invoker",
    member: "allUsers",
});

// export the url of the service
export const url = service.statuses.apply(statuses => statuses[0]?.url);
