package id.co.ptn.hungrystock.ui.privacy_police

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.ActivityPrivacyPoliceBinding
import id.co.ptn.hungrystock.ui.privacy_police.view_model.PrivacyPoliceViewModel

@AndroidEntryPoint
class PrivacyPoliceActivity : BaseActivity() {
    private lateinit var binding: ActivityPrivacyPoliceBinding
    private val viewModel: PrivacyPoliceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_police)
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar,"")
        setView()
        setListener()
    }

    private fun setView() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadData(data(), "text/html", "utf-8")
    }

    private fun setListener() {
        binding.btAgree.setOnClickListener {
            setResult(SUCCESS)
            finish()
        }
    }

    private fun data(): String{
        return "<html>\n" +
                "\n" +
                "<h3>Privacy Policy &amp; Terms and Conditions for Hungrystock</h3>\n" +
                "\n" +
                "            <p></p>\n" +
                "<h3>Privacy Policy</h3>\n" +
                "\n" +
                "<p>At Hungrystock, accessible from https://www.hungrystock.com/, one of our main priorities is the privacy of our\n" +
                "    visitors. This Privacy Policy document contains types of information that is collected and recorded by Hungrystock\n" +
                "    and how we use it.</p>\n" +
                "\n" +
                "<p>If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us.\n" +
                "</p>\n" +
                "\n" +
                "<p>This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to\n" +
                "    the information that they shared and/or collect in Hungrystock. This policy is not applicable to any information\n" +
                "    collected offline or via channels other than this website.</p>\n" +
                "\n" +
                "<h4>Consent</h4>\n" +
                "\n" +
                "<p>By using our apps, you hereby consent to our Privacy Policy and agree to its terms.</p>\n" +
                "\n" +
                "<h4>Information we collect</h4>\n" +
                "\n" +
                "<p>The personal information that you are asked to provide, and the reasons why you are asked to provide it, will be made\n" +
                "    clear to you at the point we ask you to provide your personal information.</p>\n" +
                "<p>If you contact us directly, we may receive additional information about you such as your name, email address, phone\n" +
                "    number, the contents of the message and/or attachments you may send us, and any other information you may choose to\n" +
                "    provide.</p>\n" +
                "<p>When you register for an Account, we may ask for your contact information, including items such as name, address,\n" +
                "    email address, and telephone number.</p>\n" +
                "\n" +
                "<h4>How we use your information</h4>\n" +
                "\n" +
                "<p>We use the information we collect in various ways, including to:</p>\n" +
                "\n" +
                "<ul>\n" +
                "    <li>Provide, operate, and maintain our apps</li>\n" +
                "    <li>Improve, personalize, and expand our apps</li>\n" +
                "    <li>Understand and analyze how you use our apps</li>\n" +
                "    <li>Develop new products, services, features, and functionality</li>\n" +
                "    <li>Communicate with you, either directly or through one of our partners, including for customer service, to provide\n" +
                "        you with updates and other information relating to the apps</li>\n" +
                "    <li>Send you emails</li>\n" +
                "    <li>Find and prevent fraud</li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "<h4>GDPR Data Protection Rights</h4>\n" +
                "\n" +
                "<p>We would like to make sure you are fully aware of all of your data protection rights. Every user is entitled to the\n" +
                "    following:</p>\n" +
                "<p>The right to access – You have the right to request copies of your personal data. We may charge you a small fee for\n" +
                "    this service.</p>\n" +
                "<p>The right to rectification – You have the right to request that we correct any information you believe is inaccurate.\n" +
                "    You also have the right to request that we complete the information you believe is incomplete.</p>\n" +
                "<p>The right to erasure – You have the right to request that we erase your personal data, under certain conditions.</p>\n" +
                "<p>The right to restrict processing – You have the right to request that we restrict the processing of your personal\n" +
                "    data, under certain conditions.</p>\n" +
                "<p>The right to object to processing – You have the right to object to our processing of your personal data, under\n" +
                "    certain conditions.</p>\n" +
                "<p>If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please\n" +
                "    contact us.</p>\n" +
                "\n" +
                "<h4>Children's Information</h4>\n" +
                "<p>Another part of our priority is adding protection for children while using the internet. We encourage parents and\n" +
                "    guardians to observe, participate in, and/or monitor and guide their online activity.</p>\n" +
                "<p> Hungrystock does not knowingly collect any Personal Identifiable Information from children under the age of 13. If\n" +
                "    you think that your child provided this kind of information on our website, we strongly encourage you to contact us\n" +
                "    immediately and we will do our best efforts to promptly remove such information from our records</p>\n" +
                "\n" +
                "            <br><br>\n" +
                "\n" +
                "<h3><strong>Terms and Conditions</strong></h3>\n" +
                "<p>\n" +
                "    By downloading or using the app, these terms will\n" +
                "    automatically apply to you – you should make sure therefore\n" +
                "    that you read them carefully before using the app. You’re not\n" +
                "    allowed to copy, or modify the app, any part of the app, or\n" +
                "    our trademarks in any way. You’re not allowed to attempt to\n" +
                "    extract the source code of the app, and you also shouldn’t try\n" +
                "    to translate the app into other languages, or make derivative\n" +
                "    versions. The app itself, and all the trade marks, copyright,\n" +
                "    database rights and other intellectual property rights related\n" +
                "    to it, still belong to Hungrystock.\n" +
                "</p>\n" +
                "<p>\n" +
                "    Hungrystock is committed to ensuring that the app is\n" +
                "    as useful and efficient as possible. For that reason, we\n" +
                "    reserve the right to make changes to the app or to charge for\n" +
                "    its services, at any time and for any reason. We will never\n" +
                "    charge you for the app or its services without making it very\n" +
                "    clear to you exactly what you’re paying for.\n" +
                "</p>\n" +
                "<p>\n" +
                "    The Hungrystock app stores and processes personal data that\n" +
                "    you have provided to us, in order to provide our\n" +
                "    Service. It’s your responsibility to keep your phone and\n" +
                "    access to the app secure. We therefore recommend that you do\n" +
                "    not jailbreak or root your phone, which is the process of\n" +
                "    removing software restrictions and limitations imposed by the\n" +
                "    official operating system of your device. It could make your\n" +
                "    phone vulnerable to malware/viruses/malicious programs,\n" +
                "    compromise your phone’s security features and it could mean\n" +
                "    that the Hungrystock app won’t work properly or at all.\n" +
                "</p>\n" +
                "<div>\n" +
                "    <p>\n" +
                "        The app does use third party services that declare their own\n" +
                "        Terms and Conditions.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        Link to Terms and Conditions of third party service\n" +
                "        providers used by the app\n" +
                "    </p>\n" +
                "    <ul>\n" +
                "        <li><a href=\"https://firebase.google.com/terms/analytics\" target=\"_blank\" rel=\"noopener noreferrer\">Google\n" +
                "                Analytics for Firebase</a></li>\n" +
                "    </ul>\n" +
                "</div>\n" +
                "<p>\n" +
                "    You should be aware that there are certain things that\n" +
                "    Hungrystock will not take responsibility for. Certain\n" +
                "    functions of the app will require the app to have an active\n" +
                "    internet connection. The connection can be Wi-Fi, or provided\n" +
                "    by your mobile network provider, but Hungrystock    cannot take responsibility for the app not working at full\n" +
                "    functionality if you don’t have access to Wi-Fi, and you don’t\n" +
                "    have any of your data allowance left.\n" +
                "</p>\n" +
                "<p></p>\n" +
                "<p>\n" +
                "    If you’re using the app outside of an area with Wi-Fi, you\n" +
                "    should remember that your terms of the agreement with your\n" +
                "    mobile network provider will still apply. As a result, you may\n" +
                "    be charged by your mobile provider for the cost of data for\n" +
                "    the duration of the connection while accessing the app, or\n" +
                "    other third party charges. In using the app, you’re accepting\n" +
                "    responsibility for any such charges, including roaming data\n" +
                "    charges if you use the app outside of your home territory\n" +
                "    (i.e. region or country) without turning off data roaming. If\n" +
                "    you are not the bill payer for the device on which you’re\n" +
                "    using the app, please be aware that we assume that you have\n" +
                "    received permission from the bill payer for using the app.\n" +
                "</p>\n" +
                "<p>\n" +
                "    Along the same lines, Hungrystock cannot always take\n" +
                "    responsibility for the way you use the app i.e. You need to\n" +
                "    make sure that your device stays charged – if it runs out of\n" +
                "    battery and you can’t turn it on to avail the Service,\n" +
                "    Hungrystock cannot accept responsibility.\n" +
                "</p>\n" +
                "<p>\n" +
                "    With respect to Hungrystock’s responsibility for your\n" +
                "    use of the app, when you’re using the app, it’s important to\n" +
                "    bear in mind that although we endeavour to ensure that it is\n" +
                "    updated and correct at all times, we do rely on third parties\n" +
                "    to provide information to us so that we can make it available\n" +
                "    to you. Hungrystock accepts no liability for any\n" +
                "    loss, direct or indirect, you experience as a result of\n" +
                "    relying wholly on this functionality of the app.\n" +
                "</p>\n" +
                "<p>\n" +
                "    At some point, we may wish to update the app. The app is\n" +
                "    currently available on Android – the requirements for\n" +
                "    system(and for any additional systems we\n" +
                "    decide to extend the availability of the app to) may change,\n" +
                "    and you’ll need to download the updates if you want to keep\n" +
                "    using the app. Hungrystock does not promise that it\n" +
                "    will always update the app so that it is relevant to you\n" +
                "    and/or works with the Android version that you have\n" +
                "    installed on your device. However, you promise to always\n" +
                "    accept updates to the application when offered to you, We may\n" +
                "    also wish to stop providing the app, and may terminate use of\n" +
                "    it at any time without giving notice of termination to you.\n" +
                "    Unless we tell you otherwise, upon any termination, (a) the\n" +
                "    rights and licenses granted to you in these terms will end;\n" +
                "    (b) you must stop using the app, and (if needed) delete it\n" +
                "    from your device.\n" +
                "</p>\n" +
                "<p><strong>Changes to This Terms and Conditions</strong></p>\n" +
                "<p>\n" +
                "    We may update our Terms and Conditions\n" +
                "    from time to time. Thus, you are advised to review this page\n" +
                "    periodically for any changes. We will\n" +
                "    notify you of any changes by posting the new Terms and\n" +
                "    Conditions on this page.\n" +
                "</p>\n" +
                "<p>\n" +
                "    These terms and conditions are effective as of 2023-01-11\n" +
                "</p>\n" +
                "<p><strong>Contact Us</strong></p>\n" +
                "<p>\n" +
                "    If you have any questions or suggestions about our\n" +
                "    Terms and Conditions, do not hesitate to contact us\n" +
                "    at hungrystock8b@gmail.com.\n" +
                "</p>\n" +
                "\n" +
                "            <br><br><br>\n" +
                "\n" +
                "</html>"
    }
}