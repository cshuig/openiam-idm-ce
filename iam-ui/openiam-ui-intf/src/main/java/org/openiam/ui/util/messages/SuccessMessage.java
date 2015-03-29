package org.openiam.ui.util.messages;

public enum SuccessMessage {

    CHALLENGE_RESPONSE_QUESTION_SAVED("openiam.ui.webconsole.challenge.response.question.saved.success"),
    CHALLENGE_RESPONSE_QUESTION_DELETED("openiam.ui.webconsole.challenge.response.question.deleted.success"),
    SUBSCRIBED_REPORT_DELETED("openiam.ui.selfservice.report.subscribe.deleted.success"),
    VIEW_REPORT_DELETED("openiam.ui.selfservice.report.view.deleted.success"),
    SUBSCRIPTION_TEST_START_SUCCESS("openiam.ui.selfservice.report.subscribe.test.start.success"),

    SUBSCRIBED_REPORT_SAVED("openiam.ui.selfservice.report.subscribe.saved.success"),
    REPORT_SAVED("openiam.ui.webconsole.report.saved.success"),
    REPORT_DELETED("openiam.ui.webconsole.report.deleted.success"),
    REPORT_PARAMETER_SAVED("openiam.ui.webconsole.report.param.saved.success"),
    REPORT_PARAMETER_DELETED("openiam.ui.webconsole.report.param.deleted.success"),
    REPORT_PARAMETERS_ACCEPTED("openiam.ui.webconsole.report.params.accept.success"),

    POLICY_SAVED("openiam.ui.webconsole.policy.saved.success"),
    POLICY_DELETED("openiam.ui.webconsole.policy.deleted.success"),
    RESOURCE_SAVED("openiam.ui.webconsole.resource.saved.success"),
    RESOURCE_DELETED("openiam.ui.webconsole.resource.deleted.success"),
    MENU_TREE_SAVED("openiam.ui.webconsole.menu.saved.success"),
    RESOURCE_PROPERTY_SAVED("openiam.ui.webconsole.resource.property.saved.success"),
    RESOURCE_PROPERT_DELETED("openiam.ui.webconsole.resource.property.delete.success"),

    CHILD_RESOURCE_ADDED("openiam.ui.webconsole.resource.child.add.success"),
    CHILD_RESOURCE_DELETED("openiam.ui.webconsole.resource.child.add.deleted"),
    ENTITY_ADDED("openiam.ui.webconsole.entity.add.success"),
    ENTITY_DELETED("openiam.ui.webconsole.entity.delete.success"),

    ROLE_SAVED("openiam.ui.webconsole.role.save.success"),
    ROLE_DELETE("openiam.ui.webconsole.role.delete.success"),

    ROLE_ATTRIBUTE_SAVED("openiam.ui.webconsole.role.attribute.save.success"),
    ROLE_ATTRIBUTE_DELETED("openiam.ui.webconsole.role.attribute.delete.success"),

    GROUP_SAVED("openiam.ui.webconsole.group.save.success"),
    GROUP_DELETED("openiam.ui.webconsole.group.delete.success"),
    GROUP_ATTRIBUTE_SAVED("openiam.ui.webconsole.group.attribute.save.success"),
    GROUP_ATTRIBUTE_DELETED("openiam.ui.webconsole.group.attribute.delete.success"),

    ORGANIZTION_SAVED("openiam.ui.webconsole.organization.save.success"),
    ORGANIZTION_DELETED("openiam.ui.webconsole.organization.delete.success"),
    ATTRIBUTE_SAVED("openiam.ui.webconsole.attribute.save.success"),
    ATTRIBUTE_DELETED("openiam.ui.webconsole.attribute.delete.success"),

    USER_INFO_SAVED("openiam.ui.webconsole.user.info.saved.success"),
    USER_INFOPROV_SAVED("openiam.ui.webconsole.user.infoprov.saved.success"),
    USER_DELETED("openiam.ui.webconsole.user.delete.success"),
    USER_REMOVED("openiam.ui.webconsole.user.removed.success"),
    USER_DISABLED("openiam.ui.webconsole.user.disabled.success"),
    USER_ENABLED("openiam.ui.webconsole.user.enabled.success"),
    USER_ACTIVATED("openiam.ui.webconsole.user.activated.success"),
    SECURITY_QUESTIONS_RESET("openiam.ui.webconsole.user.security.questions.reset"),
    USER_ACCOUNT_RESET("openiam.ui.webconsole.user.account.reset.success"),

    USER_EMAIL_SAVED("openiam.ui.webconsole.user.email.saved.success"),
    USER_EMAIL_DELETED("openiam.ui.webconsole.user.email.deleted.success"),
    USER_ADDRESS_SAVED("openiam.ui.webconsole.user.address.saved.success"),
    USER_ADDRESS_DELETED("openiam.ui.webconsole.user.address.deleted.success"),
    USER_PHONE_DELETED("openiam.ui.webconsole.user.phone.deleted.success"),
    USER_PHONE_SAVED("openiam.ui.webconsole.user.phone.saved.success"),

    AUTH_PROVIDER_SAVED("openiam.ui.am.auth.provider.saved.success"),
    AUTH_PROVIDER_DELETED("openiam.ui.am.auth.provider.delete.success"),
    AUTH_ATTRIBUTE_DELETED("openiam.ui.am.auth.provider.attribute.delete.success"),
    AUTH_ATTRIBUTE_SAVED("openiam.ui.am.auth.provider.attribute.save.success"),

    USER_ATTRIBUTE_DELETED("openiam.ui.webconsole.user.attribute.delete.success"),
    USER_ATTRIBUTE_SAVED("openiam.ui.webconsole.user.attribute.saved.success"),
    CONTECNT_PROVIDER_SAVED("openiam.ui.am.content.provider.saved.success"),
    CONTENT_PROVIDER_DELETED("openiam.ui.am.content.provider.deleted.success"),
    CONTENT_PROVIDER_SERVER_SAVED("openiam.ui.am.content.provider.server.saved.success"),
    CONTENT_PROVIDER_SERVER_DELETED("openiam.ui.am.content.provider.server.deleted.success"),
    CONTENT_PROVIDER_URI_PATTERN_SAVED("openiam.ui.am.uri.pattern.saved.success"),
    CONTENT_PROVIDER_URI_PATTERN_DELETED("openiam.ui.am.uri.pattern.deleted.success"),
    URI_PATTERN_META_SAVED("openiam.ui.am.uri.pattern.meta.saved.success"),
    URI_PATTERN_META_DELETED("openiam.ui.am.uri.pattern.meta.deleted.success"),

    LOGIN_SAVED("openiam.ui.selfservice.login.saved"),
    LOGIN_DELETED("openiam.ui.selfservice.login.deleted"),

    UNLOCK_EMAIL_SENT("openiam.ui.idp.password.unlock.email.sent"),
    LOGIN_REMINDER_SENT("openiam.ui.idp.password.login.reminder.sent"),
    PASSWORD_CHANGED("openiam.ui.idp.change.password.success"),
    CHALLENGE_RESPONSE_SAVED("openiam.ui.selfservice.challenge.response.saved"),
    USER_DELEGETION_FILTER_SAVED("openiam.ui.user.delegation.save.success"),
    CUSTOM_FIELD_SAVED("openiam.ui.webconsole.custom.field.save.success"),
    CUSTOM_FIELD_DELETED("openiam.ui.webconsole.custom.field.delete.success"),
    PAGE_TEMPLATE_SAVED("openiam.ui.webconsole.page.template.save.success"),
    PAGE_TEMPLATE_DELETED("openiam.ui.webconsole.page.template.delete.success"),

    NEW_HIRE_PROCESS_CREATED("openiam.ui.selfservice.tasks.process.created"),
    SELF_REGISTRATION_SUCCESSFUL("openiam.ui.selfservice.self.registration.success"),
    USER_PROFILE_SAVED("openiam.ui.selfservice.profile.save.success"),
    PASSWORD_RESETED("openiam.ui.webconsole.user.reset.password.success"),
    ATTRIBUTE_MAP_SAVED("openiam.ui.webconsole.attrMap.saved.success"),

    USER_IDENTITY_DELETED("openiam.ui.webconsole.user.identity,deleted.success"),

    RECONCILIATION_CONFIG_SAVED("openiam.ui.webconsole.resource.recon.save.success"),
    RECONCILIATION_CONFIG_DELETED("openiam.ui.webconsole.resource.recon.delete.success"),
    TASK_CLAIMED("openiam.ui.selfservice.tasks.claim.success"),
    TASK_DELETED("openiam.ui.selfservice.task.delete.success"),
    TASK_COMPLETED("openiam.ui.selfservice.task.completed.success"),
    WORKFLOW_INITIATED("openiam.ui.selfservice.workflow.initiated.success"),

    APPROVER_ASSOCIAITON_SAVE("openiam.ui.webconsole.idm.approver.assoc.save.success"),
    APPROVER_ASSOCIATION_DELETE("openiam.ui.webconsole.idm.approver.assoc.delete.success"),

    ORGANIZATION_TYPE_SAVED("openiam.ui.webconsole.organization.type.save.success"),
    ORGANIZATION_TYPE_DELETED("openiam.ui.webconsole.organization.type.delete.success"),

    IT_POLICY_RESET("openiam.ui.webconsole.it.policy.reset.success"),
    IT_POLICY_SAVED("openiam.ui.webconsole.it.policy.save.success"),

    ATTESTATION_SUCCESS("openiam.ui.selfservice.attestation.success"),

    BATCH_TASK_EXECUTED_ASYNCHONOUSLY("openiam.ui.webconsole.idm.batch.task.async.execute.success"),
    BATCH_TASK_SAVE_SUCCESS("openiam.ui.webconsole.idm.batch.task.save.success"),
    BATCH_TASK_DELETE_SUCCESS("openiam.ui.webconsole.idm.batch.task.delete.success"),

    MANAGED_SYS_DELETED("openiam.ui.webconsole.idm.managed.system.delete.success"),
    CONNECTOR_DELETED("openiam.ui.webconsole.idm.connector.delete.success"),
    MANAGED_SYS_SAVED("openiam.ui.webconsole.idm.managed.system.save.success"),
    MANAGED_SYS_TEST_CONNECT("openiam.ui.webconsole.idm.managed.system.test.connection.success"),
    MANAGED_SYS_CERTIFICATE_REQUEST("openiam.ui.webconsole.idm.managed.system.requestsslcert.success"),

    SYNCH_CONFIG_START_SUCCESS("openiam.ui.webconsole.idm.synch.config.start.success"),
    SYNCH_CONFIG_TEST_SUCCESS("openiam.ui.webconsole.idm.synch.config.test.success"),

    SYNCH_REVIEW_UPDATE_SUCCESS("openiam.ui.webconsole.idm.synch.review.update.success"),
    SYNCH_REVIEW_EXECUTE_SUCCESS("openiam.ui.webconsole.idm.synch.review.execute.success"),

    UI_THEME_SAVED("openiam.ui.theme.save.success"),
    UI_THEME_DELETED("openiam.ui.theme.delete.success"),

    METADATA_TYPE_SAVED("openiam.ui.webconsole.metadata.type.save.success"),
    METADATA_TYPE_DELETED("openiam.ui.webconsole.metadata.type.delete.success"),

    METADATA_ELEMENT_SAVED("openiam.ui.webconsole.metadata.element.save.success"),
    METADATA_ELEMENT_DELETED("openiam.ui.webconsole.metadata.element.delete.success"),

    AUTH_LEVEL_GROUPING_SAVED("openiam.ui.webconsole.am.auth.level.grouping.saved"),
    AUTH_LEVEL_GROUPING_DELETED("openiam.ui.webconsole.am.auth.level.grouping.deleted"),

    RESOURCE_TYPE_SAVED("openiam.ui.webconsole.resource.type.saved"),
    RESOURCE_TYPE_DELETED("openiam.ui.webconsole.resource.type.deleted"),

    IMPERSONATION_SUCCESS("openiam.ui.webconsole.impersonation.success"),
    LANGUAGE_SAVED("openiam.ui.webconsole.languages.saved"),

    USER_IDENTITY_SENT("openiam.ui.user.identity.sent.successfully"),

    IMAGE_UPLOAD_SUCCESS("openiam.ui.image.upload.success"),
    IMAGE_DELETE_SUCCESS("openiam.ui.image.delete.success"),
    PASSWORD_RESYNC("openiam.ui.webconsole.user.resync.password.success"),
    OPERATION_ABORTED("openiam.ui.webconsole.user.operation.abort"),

    IDENTITY_SAVED("openiam.ui.webconsole.identity.saved"),
    IDENTITY_DELETED("openiam.ui.webconsole.identity.deleted"),

    ORGANIZATION_LOCATION_SAVED("openiam.ui.common.location.saved.success"),
    ORGANIZATION_LOCATION_DELETED("openiam.ui.common.location.deleted.success"),
    GROUP_BULK_OPERATION_STARTED("openiam.ui.group.bulk.op.started");

    private String messageName;

    SuccessMessage(final String messageName) {
        this.messageName = messageName;
    }

    public String getMessageName() {
        return messageName;
    }
}
