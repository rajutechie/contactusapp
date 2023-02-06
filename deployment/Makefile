create_cluster:
	eksctl create cluster -f cluster.yaml

delete_cluster:
	eksctl delete cluster -f cluster.yaml

describe_cluster:
	eksctl utils describe-stacks --region=us-west-2 --cluster=rajutechies-cluster

aws_identity:
	aws sts get-caller-identity

set_context:
	eksctl utils write-kubeconfig --cluster=rajutechies-cluster --set-kubeconfig-context=true

enable_iam_sa_provider:
	eksctl utils associate-iam-oidc-provider --cluster=rajutechies-cluster --approve

create_cluster_role:
	kubectl apply -f rbac-role.yaml

create_iam_policy:
	aws iam create-policy \
		--policy-name AWSLoadBalancerControllerIAMPolicy \
		--policy-document file://iam_policy.json

create_service_account:
	eksctl create iamserviceaccount \
      --cluster=rajutechies-cluster \
      --namespace=kube-system \
      --name=aws-load-balancer-controller \
      --attach-policy-arn=arn:aws:iam::251832974299:policy/AWSLoadBalancerControllerIAMPolicy \
      --override-existing-serviceaccounts \
      --approve

deploy_cert_manager:
	kubectl apply \
		--validate=false \
		-f https://github.com/jetstack/cert-manager/releases/download/v1.1.1/cert-manager.yaml

deploy_ingress_controller:
	kubectl apply -f v2_2_0_full.yaml

deploy_application:
	kustomize build ./k8s | kubectl apply -f -

delete_application:
	kustomize build ./k8s | kubectl delete -f -
