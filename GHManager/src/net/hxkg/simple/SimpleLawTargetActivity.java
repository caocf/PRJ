package net.hxkg.simple;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.ReasonActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleLawTargetActivity extends Activity
{	
	public static final int RESULTCODE=2015;
	
	EditText edit_firstman,secman,lawman,duty,desc,location;
	TextView text_reason;//text_checker;
	
	RadioButton b1,b2;
	
	LawSimpletargetEN lawSimpletargetEN=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpletargetlaw);
		lawSimpletargetEN=(LawSimpletargetEN) getIntent().getSerializableExtra("lawSimpletargetEN");
		initView();
	}
	
	public void Submit(View view)
	{
		if(edit_firstman.getText().toString().equals(""))
		{
			Toast.makeText(this, "请输入当事人", Toast.LENGTH_LONG).show();
			return;
		}
		if(!(b1.isChecked()||b2.isChecked()))
		{
			Toast.makeText(this, "请选择性别", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent intent=new Intent();
		if(lawSimpletargetEN==null)
			lawSimpletargetEN=new LawSimpletargetEN();
		lawSimpletargetEN.setName(edit_firstman.getText().toString());
		String string=b1.isChecked()?"男":"女";
		lawSimpletargetEN.setGender(string);
		lawSimpletargetEN.setDuty(duty.getText().toString());
		lawSimpletargetEN.setLocation(location.getText().toString());
		lawSimpletargetEN.setCert(secman.getText().toString());
		lawSimpletargetEN.setLawname(lawman.getText().toString());
		lawSimpletargetEN.setTel(desc.getText().toString());
		intent.putExtra("LawSimpletargetEN", lawSimpletargetEN);
		setResult(RESULTCODE, intent);
		finish();
	}
	
	private void initView()
	{
		edit_firstman=(EditText) findViewById(R.id.firstman);
		b1=(RadioButton) findViewById(R.id.b1);//男
		b2=(RadioButton) findViewById(R.id.b2);//女		
		secman=(EditText) findViewById(R.id.secman);//身份证		
		lawman=(EditText) findViewById(R.id.lawman);
		duty=(EditText) findViewById(R.id.duty);		
		desc=(EditText) findViewById(R.id.desc);//电话		
		location=(EditText) findViewById(R.id.location);
		
		if(lawSimpletargetEN==null)return;
		edit_firstman.setText(lawSimpletargetEN.getName());
		boolean checked="男".equals(lawSimpletargetEN.getGender())?true:false;
		b1.setChecked(checked);
		b2.setChecked(!checked);
		secman.setText(lawSimpletargetEN.getCert());
		lawman.setText(lawSimpletargetEN.getLawname());
		duty.setText(lawSimpletargetEN.getDuty());
		desc.setText(lawSimpletargetEN.getTel());
		location.setText(lawSimpletargetEN.getLocation());
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.back:
		case R.id.law:
			this.finish();
			break;
		case R.id.toreason:
			Intent intent=new Intent(this,ReasonActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.myevidence:
			break;
		default:
			break;
		}
	}	
}
