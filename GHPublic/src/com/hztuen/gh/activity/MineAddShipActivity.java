package com.hztuen.gh.activity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.utils.ImageUtils;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.RegistImage;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.RegistAddImagesAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.HttpFileUpTool;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
/*
 * 添加我的船界面
 */
public class MineAddShipActivity extends Activity {

	private RegistAddImagesAdapter add_image_adapter;
	private List<RegistImage> registImage = new ArrayList<RegistImage>();
	private GridView addimage;
	private Button prompt, regist_submit;
	private PopupWindow popupWindowType;
	private PopupWindow popupWindowArea;
	private PopupWindow popupWindowCamera;

	private PopupWindow popupwindowPrompt;
	private PopupWindow popupwindowImage;
	private TextView regist_coastal_area;
	private TextView regist_coastal_Back;
	private String mFileName;
	private Uri imageUri;
	private final String IMAGE_TYPE = "image/*";
	private EditText ed_shipname, ed_shipnum;
	/* 拍照的照片存储位置 */
	private File PHOTO_DIR = null;
	// 照相机拍照得到的图片
	private File mCurrentPhotoFile;
	private PopupWindow pop;
	private View view;
	private LinearLayout parent_view;
	private int status = 1;// 检查popwindow的状态
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;

	private  String Url = "";//提交数据链接
	private LinearLayout shipLinera;
	private LinearLayout wharfLinear;
	private TextView addimgtitle;
	private EditText wharf_name;
	private EditText wharf_num;
	private String wharfname = "";
	private String wharfnum = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_add_ship);
		init();
	}

	private void init() {
		parent_view = (LinearLayout) findViewById(R.id.parent_add);
		parent_view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupwindowPrompt != null && popupwindowPrompt.isShowing()) {
					popupwindowPrompt.dismiss();
					//prompt.setEnabled(true);
				}
				return false;
			}
		});

		/**
		 * 两个linearlayout用于不同的用户类型的提交数据的切换显示
		 * addimgtitle用于显示黄色弹出左侧的文字
		 * */
		shipLinera = (LinearLayout) findViewById(R.id.shipLinera);
		wharfLinear = (LinearLayout) findViewById(R.id.wharfLinear);
		addimgtitle = (TextView) findViewById(R.id.addimgtitle);
		wharf_name = (EditText) findViewById(R.id.wharf_name);
		
		wharf_num = (EditText) findViewById(R.id.wharf_num);
		
		addimage = (GridView) findViewById(R.id.add_image);
		prompt = (Button) findViewById(R.id.prompt);
		prompt.setOnClickListener(onclick);
		registImage.add(new RegistImage());
		addimage.setOnItemClickListener(onitemclick);

		add_image_adapter = new RegistAddImagesAdapter(getApplicationContext(),
				registImage);
		addimage.setAdapter(add_image_adapter);
		//regist_coastal_area = (TextView) findViewById(R.id.regist_coastal_area);
		regist_coastal_Back = (TextView) findViewById(R.id.regist_coastal_Back);
		regist_coastal_Back.setOnClickListener(onclick);
		ed_shipname = (EditText) findViewById(R.id.company);
		ed_shipnum = (EditText) findViewById(R.id.Blnumber);

		regist_submit = (Button) findViewById(R.id.regist_submit);
		regist_submit.setOnClickListener(onclick);
		/**
		 * 三种用户类型都会传递参数过来
		 * 也可能是四种
		 * 通过这几个参数来修改界面显示
		 * 修改提交的值
		 * */
		Intent intent = getIntent();
		if(intent.getStringExtra("usertype")!=null){
			if(intent.getStringExtra("usertype").equals("ship")){
				Url = contants.addmyship;
				addimgtitle.setText("添加照片(船舶国际证书)");
				wharfLinear.setVisibility(View.GONE);
				shipLinera.setVisibility(View.VISIBLE);
			}else if(intent.getStringExtra("usertype").equals("wharf")){
				Url = contants.addwharf;
				addimgtitle.setText("添加照片(工商营业执照)");
				shipLinera.setVisibility(View.GONE);
				wharfLinear.setVisibility(View.VISIBLE);
			}else if(intent.getStringExtra("usertype").equals("")){
				addimgtitle.setText("添加照片(船舶国际证书)");
				wharfLinear.setVisibility(View.GONE);
				shipLinera.setVisibility(View.VISIBLE);
			}else{
				addimgtitle.setText("添加照片(船舶国际证书)");
				wharfLinear.setVisibility(View.GONE);
				shipLinera.setVisibility(View.VISIBLE);
			}
		}
		
		
		getNewImageUri();
	}

	/*
	 * popimage 的监听事件
	 */
	public OnClickListener onImageClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.image_back:

				popupwindowImage.dismiss();
				break;
				// case R.id.image_delete:
				// registImage.remove(location);
				// popupwindowImage.dismiss();
				// break;
			default:
				break;
			}
		}
	};

	public OnItemClickListener onitemclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.add_image:
				expressItemClick(position);// position 代表你点的哪一个

				break;
			}

		}

		private void expressItemClick(int position) {
			// TODO Auto-generated method stub
			if (position == registImage.size() - 1) {
				initPopupWindowCamera();
			} else {
				// initPopupWindowCamera();
				initPopWindowImage(position);
			}

			// 看你需不需要返回当前界面，如果点返回需要返回到当前界面，就不用这个
		}

	};

	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.regist_coastal_Back:
				finish();
			case R.id.prompt:
				//prompt.setEnabled(false);
				initPopWindowPrompt();
				break;
			case R.id.regist_submit:
				AddSubmit();
				break;
			default:
				break;
			}
		}
	};

	public OnClickListener onclicklis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.camera_photo:

				doTakePhoto();
				popDissmiss3();
				break;
			case R.id.camera_images:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType(IMAGE_TYPE);
				startActivityForResult(intent, 1001);
				popDissmiss3();
				break;
			case R.id.camera_cancel:
				popDissmiss3();
				break;
			default:
				break;
			}
		}
	};

	public void initPopupWindowCamera() {
		View contentView = getLayoutInflater()
				.inflate(R.layout.popcamera, null);
		TextView takephoto = (TextView) contentView
				.findViewById(R.id.camera_photo);
		TextView takeimage = (TextView) contentView
				.findViewById(R.id.camera_images);
		TextView takecancel = (TextView) contentView
				.findViewById(R.id.camera_cancel);

		takephoto.setOnClickListener(onclicklis);
		takeimage.setOnClickListener(onclicklis);
		takecancel.setOnClickListener(onclicklis);

		LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_add);// 父窗口view
		popupWindowCamera = new PopupWindow(contentView,
				parent.getWidth() * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowCamera.setFocusable(true);
		popupWindowCamera.setOutsideTouchable(false);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		popupWindowCamera.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});

		popupWindowCamera.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

	public void initPopWindowPrompt() {
		View contentView = getLayoutInflater().inflate(
				R.layout.pop_regist_prompt, null);

		LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_add);// 父窗口view
		popupwindowPrompt = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popupwindowPrompt.setFocusable(false);
		popupwindowPrompt.setOutsideTouchable(true);
		
		popupwindowPrompt.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupwindowPrompt.setBackgroundDrawable(getResources().getDrawable(R.drawable.zhanwie));
		
		popupwindowPrompt.showAsDropDown(addimgtitle, (addimgtitle.getWidth()+20+prompt.getWidth()), (-prompt.getHeight()-15));

	}

	public void popDissmiss() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		getWindow().setAttributes(lp);
		popupWindowArea.dismiss();
	}

	public void popDissmiss2() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		getWindow().setAttributes(lp);
		popupWindowType.dismiss();
	}

	public void popDissmiss3() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		getWindow().setAttributes(lp);
		popupWindowCamera.dismiss();
	}

	public void popDissmiss4() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		getWindow().setAttributes(lp);
		popupwindowImage.dismiss();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent mIntent) {
		switch (requestCode) {
		case CAMERA_WITH_DATA:
			if (imageUri != null) {
				// AbLogUtil.d(getActivity(), "裁剪后得到的图片的路径是 = " + path);
				Bitmap bitmap = getLoacalBitmap(imageUri.getPath()); // 从本地取图片
				if (bitmap == null) {
					// Util.getTip(getApplicationContext(), "bitmap为空");
				} else {
					Drawable drawble = new BitmapDrawable(bitmap);
					RegistImage mRegistImage = new RegistImage();
					mRegistImage.setImageFolder(imageUri.getPath());
					registImage.add(mRegistImage);
					add_image_adapter.notifyDataSetChanged();
					//
					getNewImageUri();
				}
			} else {
				Toast.makeText(getApplicationContext(), "图片获取失败",
						Toast.LENGTH_LONG).show();
			}
			break;
			// case 1001:
			// break;
		default:
			break;
		}

		if (resultCode != RESULT_OK) { // 此处的 RESULT_OK 是系统自定义得一个常量
			// Log.e(TAG,"ActivityResult resultCode error");
			return;
		}
		Bitmap bm = null;
		// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

		ContentResolver resolver = getContentResolver();

		// 此处的用于判断接收的Activity是不是你想要的那个

		if (requestCode == 1001) {

			try {

				Uri originalUri = mIntent.getData(); // 获得图片的uri
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				String[] proj = { MediaStore.Images.Media.DATA };

				// 好像是android多媒体数据库的封装接口，具体的看Android文档

				Cursor cursor = managedQuery(originalUri, proj, null, null,
						null);

				// 按我个人理解 这个是获得用户选择的图片的索引值

				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

				// 将光标移至开头 ，这个很重要，不小心很容易引起越界

				cursor.moveToFirst();

				// 最后根据索引值获取图片路径

				String path = cursor.getString(column_index);
				Bitmap bitmap = getLoacalBitmap(path); // 从本地取图片

				Drawable drawble = new BitmapDrawable(bitmap);
				RegistImage mRegistImage = new RegistImage();
				mRegistImage.setImageFolder(path);
				registImage.add(mRegistImage);
				add_image_adapter.notifyDataSetChanged();
				//
				getNewImageUri();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * update imagePath uri
	 */
	private void getNewImageUri() {
		String path = getSDCardPath();
		PHOTO_DIR = new File(path + File.separator + "temp_"
				+ System.currentTimeMillis() + ".png");
		imageUri = Uri.fromFile(PHOTO_DIR);
	}

	/**
	 * 拍照获取图片
	 */
	protected void doTakePhoto() {
		try {
			mFileName = System.currentTimeMillis() + ".jpg";
			// mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			// intent.putExtra(MediaStore.EXTRA_OUTPUT,
			// Uri.fromFile(mCurrentPhotoFile));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "未找到系统相机",
					Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * 查看图片
	 */
	public void initPopWindowImage(final int position) {
		View contenView = getLayoutInflater().inflate(
				R.layout.pop_coastal_image, null);
		ImageView image_state = (ImageView) contenView
				.findViewById(R.id.image_state);// 等待存放的图片
		// image_state
		Bitmap bm = BitmapFactory.decodeFile(registImage.get(position + 1)
				.getImageFolder());
		image_state.setImageBitmap(bm);

		// registImage.get(position);
		// image_state.setBackground(registImage.get(position));
		ImageView image_delete = (ImageView) contenView
				.findViewById(R.id.image_delete);
		TextView image_back = (TextView) contenView
				.findViewById(R.id.image_back);
		image_back.setText(position + 1 + "/" + (registImage.size() - 1));
		image_back.setOnClickListener(onImageClick);
		image_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				registImage.remove(position + 1);

				add_image_adapter.notifyDataSetChanged();
				popupwindowImage.dismiss();
			}
		});
		LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_add);// 父窗口view
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);

		popupwindowImage = new PopupWindow(contenView, parent.getWidth(),
				parent.getHeight());
		popupwindowImage.setFocusable(true);
		popupwindowImage.setOutsideTouchable(false);

		popupwindowImage.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});

		popupwindowImage.showAtLocation(parent, Gravity.CENTER, 0, 30);
	}

	/**
	 * 加载本地图片
	 *
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				if (lineStr.contains("sdcard")
						&& lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure",
								"");
						return result;
					}
				}
				// 检查命令是否执行失败。
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0表示正常结束，1：非正常结束
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {

			return Environment.getExternalStorageDirectory().getPath();
		}

		return Environment.getExternalStorageDirectory().getPath();
	}

	/*
	 * 注册提交
	 */
	@SuppressWarnings("unused")
	public void AddSubmit() {

		String Shipname = ed_shipname.getText().toString();
		String Shipnum = ed_shipnum.getText().toString();
		wharfnum = wharf_num.getText().toString();
		wharfname = wharf_name.getText().toString();
		// 访问网络
		// KJHttp kj = new KJHttp();
		final Map<String, Object> params = new HashMap<String, Object>();
		// 访问地址
		// final String toUrl = "http://120.55.193.1:8080/TEST/filetest";
		final String toUrl = Url;
		final String resultcode;
		/*
		 * 文件上传
		 */
		final HttpFileUpTool hft = new HttpFileUpTool();
		final Map<String, File> files = new HashMap<String, File>();
		for (int i = 0; i < registImage.size() - 1; i++) {
			File mFile = ImageUtils.getSmallImageFile(getApplicationContext(),
					registImage.get(i + 1).getImageFolder(), 300, 300, true);

			Intent intent = getIntent();
			if(intent.getStringExtra("usertype")!=null){
				if(intent.getStringExtra("usertype").equals("ship")){
					if (Shipname.equals("")) {
						Toast.makeText(getApplicationContext(), "缺少船名号",
								Toast.LENGTH_LONG).show();
						return;
					}else{
						params.put("Shipname", Shipname);
					}
					if (Shipnum.equals("")) {
						Toast.makeText(getApplicationContext(), "缺少船舶识别号",
								Toast.LENGTH_LONG).show();
						return;
					}else{
						params.put("Shipnum", Shipnum);
					}
					if (mFile != null) {
						files.put(mFile.getName(), mFile);

					} else {
						Toast.makeText(getApplicationContext(), "文件缺失",
								Toast.LENGTH_LONG).show();
					}
				}else if(intent.getStringExtra("usertype").equals("wharf")){
					
					if (wharfname.equals("")) {
						Toast.makeText(getApplicationContext(), "缺少码头名称",
								Toast.LENGTH_LONG).show();
						return;
					}else{
						params.put("Wharfname", wharfname);
					}
					if (wharfnum.equals("")) {
						Toast.makeText(getApplicationContext(), "缺少码头编号",
								Toast.LENGTH_LONG). show();
						return;
					}else{
						params.put("Wharfnum", wharfnum);
					}
					if (mFile != null) {
						files.put(mFile.getName(), mFile);

					} else {
						Toast.makeText(getApplicationContext(), "文件缺失",
								Toast.LENGTH_LONG).show();
					}
				}else if(intent.getStringExtra("usertype").equals("")){
				}else{
				}
			}
			
		}

		try {

			if (files.size() <= 0) {
				Toast.makeText(getApplicationContext(), "文件缺失",
						Toast.LENGTH_LONG).show();
				return;
			}
			for (int i = 0; i < registImage.size() - 1; i++) {
				File mFile =
						ImageUtils.getSmallImageFile(getApplicationContext(),
								registImage.get(i + 1).getImageFolder(), 300, 300, true);

				params.put(mFile.getName(), mFile);
			}

			//	params.put("img", files);
			params.put("Username", SystemStatic.userName);
			

			if (params == null && params.equals("")) {
				// 提示参数制造失败
				Util.getTip(getApplicationContext(), "构造参数失败");
			} else {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							final String result = hft.post(toUrl, params,
									files, "img");

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									String resu = result.trim();
									JSONObject res = null;
									try {
										res = new JSONObject(resu);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									int resultcode = 0;
									try {
										resultcode = res.getInt("resultcode");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (resultcode < 0) {
										Toast.makeText(getApplicationContext(),
												"添加失败", Toast.LENGTH_LONG)
												.show();
									} else if (resultcode >= 0) {
										Toast.makeText(getApplicationContext(),
												"添加成功", Toast.LENGTH_LONG)
												.show();
										Intent mIntent = new Intent();
										Intent intent = getIntent();
										if(intent.getStringExtra("usertype")!=null){
											if(intent.getStringExtra("usertype").equals("ship")){
												mIntent.setClass(
														getApplicationContext(),
														MineChuanHuActivity.class);
											}else if(intent.getStringExtra("usertype").equals("wharf")){
												mIntent.setClass(
														getApplicationContext(),
														MineMaTouActivity.class);
											}else if(intent.getStringExtra("usertype").equals("")){
											}else{
											}
										}
										
										startActivity(mIntent);
									} else {
										Toast.makeText(getApplicationContext(),
												"请检查网络", Toast.LENGTH_LONG)
												.show();
									}

								}
							});

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();

			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

}
