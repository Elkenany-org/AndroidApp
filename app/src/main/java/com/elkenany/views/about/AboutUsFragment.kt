package com.elkenany.views.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.R
import com.elkenany.databinding.FragmentAboutUsBinding
import com.elkenany.viewmodels.AboutUsViewModel
import com.elkenany.viewmodels.ViewModelFactory

class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutUsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AboutUsViewModel

    private val desc =
        "<div _ngcontent-aeh-c92=\"\" class=\"card__main\"><h1 _ngcontent-aeh-c92=\"\" class=\"main__title\">من نحن</h1><div _ngcontent-aeh-c92=\"\" class=\"description\"><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">نحن نشرف بمرور خمسة عشرة عاماً على تأسيس مجموعة الكناني ، فمنذ أن تأسست الشركة في عام 2008 في مدينة المنصورة بمحافظة الدقهلية ، ونحن نسعى إلي توفير احتياجات العملاء وأفضل الخدمات للوصول إلى إرضائهم&nbsp; لنتمكن من تكوين أكبر قاعدة داخل وخارج السوق المصرى في مجالات متنوعة تخدم المجال البيطري والزراعي ( القطاع الداجني - الحيواني - الزراعى - السمكي - الخيول العربية ).</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\"> &nbsp;مجموعة الكناني دائماً تعمل على التميز والنجاح من خلال أعضاء فريق عمل مؤهل ومدرب على أحدث التقنيات لخدمة السادة العملاء والموردين والمساهمين والمجتمع الذي تخدمه.</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">ولأننا نبحث عن تطوير الشركة والإرتقاء بها فنحن نتلقى ونرحب بإقتراحاتكم ولا نطرحها جانبا بل نجاهد إلى تلبيتها ، لأن هدفنا جميعاَ الوصول إلى الرفع من الشأن الإقتصادي المصري بشكل خاص والعربي بشكل عام&nbsp;</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\">&nbsp;</p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">بعد البحث لفترات طويلة عن قاعدة بيانات حقيقية&nbsp; وواضحة لكل من يعمل في المجال بالسوق المحلى ( البيطري - الزراعي ) وبالتزامن مع بدء إنتشار عمليات الإستغلال من قبل الوسطاء نبتت فكرة أول وأكبر تطبيق خدمي في المجال البيطري والزراعي لجميع أنواع الهواتف المحمولة في عام 2018 .</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">ولحرص مجموعة الكناني الدائم على مصلحة جميع العاملين بالمجال ونقل التطور التكنولوجى إلى المجال البيطري والزراعي بجميع قطاعاته بشكل متكامل ، قامت الشركه بإطلاق وتطويرالموقع الإلكتروني والتطبيق ليكون شامل لخدمة جميع العاملين في القطاعات ( الإنتاج الداجنى&nbsp; - والحيواني&nbsp; - والزراعي - والسمكي - والخيول العربية الأصيلة ) فى مصر بشكل خاص والوطن العربى بشكل عام .</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">وبذلك فقد تمكنا من فعل أول سبق وثورة تكنولوجية في جميع قطاعات المجال البيطري والزراعي ، بدمج التكنولوجيا والبيانات لتسهيل الوصول على العاملين بالقطاعات .</strong></p><h2 _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">المهمة :&nbsp;</strong></h2><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">نسعى دائماً لتطوير خدمات الشركة والارتقاء بمستوى الأداء المؤسسي لتلبية احتياجات العملاء الحاليين والوصول إلى كل العملاء المتوقعين باستخدام أحدث الأساليب التكنولوجية لتوفير قاعدة بيانات شاملة ومتكاملة لكل مايخص المجال البيطري والزراعي .</strong></p><h2 _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">الرؤية :</strong></h2><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\"><strong _ngcontent-aeh-c92=\"\">تتطلع مجموعة الكناني أن تصبح الاختيار الأول لأى عامل ومستثمر في جميع قطاعات المجال البيطري والزراعي من خلال تقديم خدمات إستثنائية قبل وبعد البيع لعملائنا وتوفير حلول إقتصادية مبتكرة .</strong></p><p _ngcontent-aeh-c92=\"\" style=\"padding-left: 40px; text-align: center;\">&nbsp;</p></div></div>"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AboutUsViewModel::class.java]
        return binding.root
    }

}