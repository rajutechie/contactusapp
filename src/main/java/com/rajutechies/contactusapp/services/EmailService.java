package com.rajutechies.contactusapp.services;

import com.rajutechies.contactusapp.models.request.ContactDetailsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EmailService {

    @Value("${app.sendgrid.api.key}")
    private String appSendgridApiKey;

    public EmailService() {

    }

    @Async
    public Boolean sendEmail(ContactDetailsRequest contactDetailsRequest)  {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(appSendgridApiKey);

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            Boolean templateStatus = true;
            Long limit = 50l;
            Long offset = 0l;
            String sort = null;
            String htmlContent = "";
            GetSmtpTemplates response = api.getSmtpTemplates(templateStatus, limit, offset, sort);
            AtomicReference<GetSmtpTemplateOverview> getSmtpTemplateOverviewContent = new AtomicReference<>(new GetSmtpTemplateOverview());
            response.getTemplates().forEach(
                    getSmtpTemplateOverview -> {
                        if (getSmtpTemplateOverview.getName().equalsIgnoreCase("thank_you")) {
                            getSmtpTemplateOverviewContent.set(getSmtpTemplateOverview);
                        }
                    }
            );
            if (null != getSmtpTemplateOverviewContent.get()) {
                htmlContent = getSmtpTemplateOverviewContent.get().getHtmlContent();
                htmlContent = htmlContent.replace("{{userName}}", contactDetailsRequest.getUserName());
                SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

                List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
                SendSmtpEmailTo to = new SendSmtpEmailTo();
                to.setEmail(contactDetailsRequest.getEmailAddress());
                to.setName(contactDetailsRequest.getUserName());
                toList.add(to);

                SendSmtpEmailSender sender = new SendSmtpEmailSender();
                sender.setEmail(getSmtpTemplateOverviewContent.get().getSender().getEmail());
                sender.setName(getSmtpTemplateOverviewContent.get().getSender().getName());

                SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
                replyTo.setEmail(getSmtpTemplateOverviewContent.get().getSender().getEmail());
                replyTo.setName(getSmtpTemplateOverviewContent.get().getSender().getName());

                Properties params = new Properties();
                params.setProperty("userName", contactDetailsRequest.getUserName());
                params.setProperty("contact.EMAIL", contactDetailsRequest.getEmailAddress());

                List<SendSmtpEmailTo1> toList1 = new ArrayList<SendSmtpEmailTo1>();
                SendSmtpEmailTo1 to1 = new SendSmtpEmailTo1();
                to1.setEmail(contactDetailsRequest.getEmailAddress());
                to1.setName(contactDetailsRequest.getUserName());
                toList1.add(to1);

                List<SendSmtpEmailMessageVersions> messageVersions = new ArrayList<>();
                SendSmtpEmailMessageVersions versions1 = new SendSmtpEmailMessageVersions();
                SendSmtpEmailMessageVersions versions2 = new SendSmtpEmailMessageVersions();
                versions1.to(toList1);
                versions2.to(toList1);
                messageVersions.add(versions1);
                messageVersions.add(versions2);

                sendSmtpEmail.sender(sender);
                sendSmtpEmail.to(toList);
                sendSmtpEmail.replyTo(replyTo);
                sendSmtpEmail.htmlContent(htmlContent);
                sendSmtpEmail.params(params);

                sendSmtpEmail.setMessageVersions(messageVersions);
                sendSmtpEmail.setTemplateId(1L);

                CreateSmtpEmail responses = api.sendTransacEmail(sendSmtpEmail);
                System.out.println(response.toString());

            }
        } catch (Exception e) {
            System.out.println("Email Exception occurred:- " + e.getMessage());
        }

        return false;
    }
}
