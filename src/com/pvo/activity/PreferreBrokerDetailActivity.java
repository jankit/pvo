package com.pvo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pvo.prototype.PVOFragment;


/**
 * This is used for display the full information of the my property on click of first text view of my property List
 * */
public class PreferreBrokerDetailActivity extends PVOFragment {
    private TextView preferreBrokerIDTxt;
    private TextView preferreBrokerNameTxt;
    private TextView preferreBrokerCompanyTxt;
    private TextView preferreBrokerAddressTxt;
    private TextView preferreBrokerNumberTxt;
    private TextView preferreBrokerWebsiteTxt; 
    private TextView preferreBrokerEmailTxt;
    private TextView preferreBrokerDateOfJoinTxt;
    private TextView preferreBrokerFacebookTxt;
    private TextView preferreBrokerTwitterTxt;
    private TextView preferreBrokerLinkedinTxt;
    private TextView preferreBrokerBusinessPageTxt;
    private TextView preferreBrokerAffiliateTxt;
    private TextView  preferreBrokerBusinessScinceTxt;
    private TextView preferreBrokerLanguageTxt;
	
    /** Text View for Display Json Array value **/
	private TextView preferreBrokerID;
	private TextView preferreBrokerName;
	private TextView preferreBrokerCompany;
	private TextView preferreBrokerAddress;
	private TextView preferreBrokerNumber;
	private TextView preferreBrokerWebsite;
	private TextView preferreBrokerEmail;
	private TextView preferreBrokerDateOfJoin;
	private TextView preferreBrokerFacebook;
	private TextView preferreBrokerTwitter;
	private TextView preferreBrokerLinkedin;
	private TextView preferreBrokerBusinessPade;
	private TextView preferreBrokerAffiliate;
	private TextView  preferreBrokerBusinessScince;
	private TextView preferreBrokerLanguage;
	private Button backButton;
	private Intent intent;
	
	/**
	 * set layout content view 
	 */
	public PreferreBrokerDetailActivity() {
		setContentView(R.layout.activity_preferrebroker_detail);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//used to handle force close 
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(getActivity()));
				
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		intent				= getActivity().getIntent();
		//Broker Id
		preferreBrokerIDTxt	= (TextView)findViewById(R.id.preferreBrokerDettv1);
		preferreBrokerID	= (TextView)findViewById(R.id.preferreBrokerDettv11);
		
		preferreBrokerID.setText(intent.getStringExtra("brokerid"));
		preferreBrokerIDTxt.setMinLines(preferreBrokerID.getLineCount());
		preferreBrokerID.setMinLines(preferreBrokerID.getLineCount());
		
		//Broker Name
		preferreBrokerNameTxt	= (TextView)findViewById(R.id.preferreBrokerNameDettv2);
		preferreBrokerName		= (TextView)findViewById(R.id.preferreBrokerNameValDettv12);
		
		preferreBrokerName.setText(intent.getStringExtra("firstname")+" "+ intent.getStringExtra("lastname"));
		preferreBrokerNameTxt.setMinLines(preferreBrokerName.getLineCount());
		preferreBrokerName.setMinLines(preferreBrokerName.getLineCount());

		//Company Name
		preferreBrokerCompanyTxt	= (TextView)findViewById(R.id.preferreBrokerCompanyNameDettv3);
		preferreBrokerCompany		= (TextView)findViewById(R.id.preferreBrokercompanyNameValDettv13);
		
		preferreBrokerCompany.setText(intent.getStringExtra("companyname"));
		preferreBrokerCompanyTxt.setMinLines(preferreBrokerCompany.getLineCount());
		preferreBrokerCompany.setMinLines(preferreBrokerCompany.getLineCount());
		
		//Address
		preferreBrokerAddressTxt	= (TextView)findViewById(R.id.preferreBrokerAddressDettv4);
		preferreBrokerAddress		= (TextView)findViewById(R.id.preferreBrokerAddressValDettv14);
		
		preferreBrokerAddress.setText(intent.getStringExtra("address")+"\n"+intent.getStringExtra("postcode"));
		preferreBrokerAddressTxt.setMinLines(preferreBrokerAddress.getLineCount());
		preferreBrokerAddress.setMinLines(preferreBrokerAddress.getLineCount());
		
		
		//Number
		preferreBrokerNumberTxt		= (TextView)findViewById(R.id.preferreBrokerNumberDettv5);
		preferreBrokerNumber		= (TextView)findViewById(R.id.preferreBrokerNumberValDettv15);
		
		preferreBrokerNumber.setText("(M) "+intent.getStringExtra("phonem")+ "\n(O)"+intent.getStringExtra("phoneo")+"\n");
		preferreBrokerNumberTxt.setLines(preferreBrokerNumber.getLineCount());
		preferreBrokerNumber.setLines(preferreBrokerNumber.getLineCount());
		
		//Website
		preferreBrokerWebsiteTxt	= (TextView)findViewById(R.id.preferreBrokerWebsiteDettv6);
		preferreBrokerWebsite		= (TextView)findViewById(R.id.preferreBrokerWebsiteValDettv16);
		
		preferreBrokerWebsite.setText(intent.getStringExtra("website")+"\n");
		preferreBrokerWebsiteTxt.setLines(preferreBrokerWebsite.getLineCount());
		preferreBrokerWebsite.setLines(preferreBrokerWebsite.getLineCount());
		
		//Email
		preferreBrokerEmailTxt 		= (TextView)findViewById(R.id.preferreBrokerEmailDetTv);
		preferreBrokerEmail 		= (TextView)findViewById(R.id.preferreBrokerEmailValTv);
		
		preferreBrokerEmail.setText(intent.getStringExtra("email")+"\n");
		preferreBrokerEmailTxt.setMinLines(preferreBrokerEmail.getLineCount());
		preferreBrokerEmail.setMinLines(preferreBrokerEmail.getLineCount());
		
		
		//Date of join
		preferreBrokerDateOfJoinTxt	= (TextView)findViewById(R.id.preferreBrokerDateOfJoinDettv7);
		preferreBrokerDateOfJoin	= (TextView)findViewById(R.id.preferreBrokerDateOfJoinValDettv7);
		
		preferreBrokerDateOfJoin.setText(intent.getStringExtra("dtjoin"));
		preferreBrokerDateOfJoinTxt.setMinLines(preferreBrokerDateOfJoin.getLineCount());
		preferreBrokerDateOfJoin.setMinLines(preferreBrokerDateOfJoin.getLineCount());
		
		//Facebook
		preferreBrokerFacebookTxt	= (TextView)findViewById(R.id.preferreBrokerFacebookDettv8);
		preferreBrokerFacebook		= (TextView)findViewById(R.id.preferreBrokerFacebookValDettv18);
		
		preferreBrokerFacebook.setText(intent.getStringExtra("facebook"));
		preferreBrokerFacebookTxt.setMinLines(preferreBrokerFacebook.getLineCount());
		preferreBrokerFacebook.setMinLines(preferreBrokerFacebook.getLineCount());
		
		
		//Twitter
		preferreBrokerTwitterTxt	= (TextView)findViewById(R.id.preferreBrokerTwitterDettv9);
		preferreBrokerTwitter		= (TextView)findViewById(R.id.preferreBrokerTwitterValDettv19);
		
		preferreBrokerTwitter.setText(intent.getStringExtra("twitter"));
		preferreBrokerTwitterTxt.setMinLines(preferreBrokerTwitter.getLineCount());
		preferreBrokerTwitter.setMinLines(preferreBrokerTwitter.getLineCount());
		
		//Linkedin
		preferreBrokerLinkedinTxt	= (TextView)findViewById(R.id.preferreBrokerLinkedinDettv10);
		preferreBrokerLinkedin		= (TextView)findViewById(R.id.preferreBrokerLinkedinValDettv20);
		
		preferreBrokerLinkedin.setText(intent.getStringExtra("linkedin"));
		preferreBrokerLinkedinTxt.setMinLines(preferreBrokerLinkedin.getLineCount());
		preferreBrokerLinkedin.setMinLines(preferreBrokerLinkedin.getLineCount());
		
		//Business Page
		preferreBrokerBusinessPageTxt = (TextView)findViewById(R.id.preferreBrokerBusinessPageDettv21);
		preferreBrokerBusinessPade	  = (TextView)findViewById(R.id.preferreBrokerBusinessPageValDettv27);
		
		preferreBrokerBusinessPade.setText(intent.getStringExtra("businessPage"));
		preferreBrokerBusinessPageTxt.setMinLines(preferreBrokerBusinessPade.getLineCount());
		preferreBrokerBusinessPade.setMinLines(preferreBrokerBusinessPade.getLineCount());
		
		//Affiliate 
		preferreBrokerAffiliateTxt	 = (TextView)findViewById(R.id.preferreBrokerAffiliateWithDettv22);
		preferreBrokerAffiliate		 = (TextView)findViewById(R.id.preferreBrokerAffiliateWithValDettv28);
		
		preferreBrokerAffiliate.setText(intent.getStringExtra("affiliatedWith"));
		preferreBrokerAffiliateTxt.setMinLines(preferreBrokerAffiliate.getLineCount());
		preferreBrokerAffiliate.setMinLines(preferreBrokerAffiliate.getLineCount());
		
		//Business Scince
		preferreBrokerBusinessScinceTxt = (TextView)findViewById(R.id.preferreBrokerBusinessScinceDettv23);
		preferreBrokerBusinessScince	= (TextView)findViewById(R.id.preferreBrokerBusinessScinceValDettv29);
		
		preferreBrokerBusinessScince.setText(intent.getStringExtra("businessScince"));
		preferreBrokerBusinessScinceTxt.setMinLines(preferreBrokerBusinessScince.getLineCount());
		preferreBrokerBusinessScince.setMinLines(preferreBrokerBusinessScince.getLineCount());
		
		//Language Known
		preferreBrokerLanguageTxt	= (TextView)findViewById(R.id.preferreBrokerLanguageKnownDettv24);
		preferreBrokerLanguage 		= (TextView)findViewById(R.id.preferreBrokerLanguageKnownValDettv30);
		
		preferreBrokerLanguage.setText(intent.getStringExtra("languageKnown"));
		preferreBrokerLanguageTxt.setMinLines(preferreBrokerLanguage.getLineCount());
		preferreBrokerLanguage.setMinLines(preferreBrokerLanguage.getLineCount());
		
		
		/** onBack Button press Move to  **/
		backButton = (Button)findViewById(R.id.preferreBrokerDetailBackBtn);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getActivity().finish();
			}		
		});
	}

	@Override
	public void processResponse(Object response) {}
}
