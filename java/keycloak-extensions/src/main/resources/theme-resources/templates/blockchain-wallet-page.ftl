<#import "template.ftl" as layout>
<@layout.registrationLayout; section>

    <#if section = "header">
        Ethereum Wallet Blockchain Authenticator (MetaMask)
    <#elseif section = "form">
        <script src="https://cdn.jsdelivr.net/npm/web3@1.5.2/dist/web3.min.js"></script>
        <source rel="stylesheet" src="https://metamask.io/css/metamask-staging-2.webflow.css"/>


        <form id="kc-wallet-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
            <#if isAuthentication??>
                <input type="hidden" id="blockchain-data" name="blockchain-data"/>
                <input type="hidden" id="blockchain-sig" name="blockchain-sig"/>
            <#else>
                <input type="hidden" id="is-sso" name="is-sso" value="true"/>
            </#if>
            <input type="hidden" id="blockchain-address" name="blockchain-address"/>
            <div id="pluginKoDivs">
                <div class="alert-error pf-m-danger">
                    <div class="pf-c-alert__icon">
                        <span class="${properties.kcFeedbackErrorIcon!}"></span>
                    </div>
                    <span class="${properties.kcAlertTitleClass!}" id="walletError"></span>

                    <a href="https://metamask.io/download.html" aria-current="page"
                       class="navigation-link install-blue w-nav-link w--current" style="max-width: 940px;">Download
                        MetaMask</a>

                    <div class="${properties.kcFormGroupClass!}">

                        <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                            <div class="${properties.kcFormButtonsWrapperClass!}">
                                <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}"
                                       name="cancel" id="kc-cancel" type="submit" value="${msg("doIgnore")}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="pluginOkDivs">
                <div class="${properties.kcFormGroupClass!} text-center">
                    <img src="https://metamask.io/images/mm-logo.svg">
                </div>
                <div class="${properties.kcFormGroupClass!} text-center">
                    <div class="${properties.kcLabelWrapperClass!}">
                        <label for="user.attributes.blockchain.address" class="${properties.kcLabelClass!}">We recognize your Blockchain
                            address as: </label>
                        <span id="address-label"></span>
                    </div>
                </div>
                <div class="${properties.kcFormGroupClass!}">

                    <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                               name="cancel" id="kc-cancel" type="submit" value="${msg("doCancel")}"/>
                        <#if isAuthentication??>
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                               name="login" id="kc-login" type="button" value="Authenticate with MetaMask"
                               onclick="javascript:authentication()"/>
                        </#if>
                    </div>
                </div>
            </div>
        </form>
    </#if>

</@layout.registrationLayout>
