<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("loginAccountTitle")}
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-form-login" action="${url.loginAction}" method="post">
                    <h3>
                        <span>${msg("roleFilter.header")}</span>
                    </h3>
                    <div class="${properties.kcFormGroupClass!}">
                        <label for="selectedRoles" class="${properties.kcLabelClass!}">${msg("roleFilter.selectLabel")}</label>
                        <select name="selectedRoles" id="selectedRoles" multiple>
                            <#list roles as role>
                                <option value="${role.name}">${role.name}</option>
                            </#list>
                        </select>
                    </div>

                    <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                        <input class=""${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!} name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>