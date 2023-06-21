<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("loginAccountTitle")}
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-form-login" action="${url.loginAction}" method="post">
                    <h3>
                        <span>${msg("captcha.header")}</span>
                    </h3>
                    <div class="${properties.kcFormGroupClass!}">
                        <img src="${url.resourcesPath}/${challengeImage}" />
                        <label for="captchaSolution" class="${properties.kcLabelClass!}">${label}</label>
                        <input type="text" name="captchaSolution">
                    </div>

                    <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                        <input class=""${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!} name="submit" id="kc-login" type="submit" value="${msg("captcha.solveButton")}"/>
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>