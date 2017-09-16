package com.huzhouport.common.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.TextView;

/**
 * dialogπ‹¿Ì∆˜
 * 
 * @author Administrator zwc
 * 
 */
public class ManagerDialog
{

	public static Dialog getDateDialog(Context context,
			OnDateSetListener listener, Calendar c)
	{
		return getDateDialog(context, listener, c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}

	public static Dialog getDateDialog(Context context,
			OnDateSetListener listener, int year, int month, int day)
	{
		Dialog dialog;
		dialog = new DatePickerDialog(context, listener, year, month, day);

		return dialog;
	}

	public static Dialog getTimeDialog(Context c, OnTimeSetListener listener,
			int hour, int minute, boolean is24hour)
	{
		Dialog dialog;
		dialog = new TimePickerDialog(c, listener, hour, minute, is24hour);

		return dialog;
	}

	public static Dialog getRadioDialog(Context ctx, String title, int itemsId,
			OnClickListener listener, String btnName, OnClickListener listener2)
	{
		return getRadioDialog(ctx, title, itemsId, listener, btnName,
				listener2, 0);
	}

	public static Dialog getRadioDialog(Context ctx, String title, int itemsId,
			OnClickListener listener, String btnName,
			OnClickListener listener2, int selectID)
	{

		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				ctx);

		builder.setTitle(title);
		builder.setSingleChoiceItems(itemsId, selectID, listener);
		builder.setPositiveButton(btnName, listener2);
		dialog = builder.create();
		return dialog;
	}

	public static Dialog getListDialog(Context ctx, String title,
			String[] data, OnClickListener listener)
	{
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				ctx);

		builder.setTitle(title);
		builder.setItems(data, listener);
		builder.setNegativeButton("", null);
		dialog = builder.create();
		return dialog;
	}

	public static Dialog editDialog(Context ctx, String title, View v,
			String name1, OnClickListener listener1, String name2,
			OnClickListener listener2, boolean canCancel)
	{
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				ctx);

		builder.setTitle(title);
		builder.setView(v);
		builder.setPositiveButton(name1, listener1);
		builder.setNegativeButton(name2, listener2);
		dialog = builder.create();
		dialog.setCancelable(canCancel);
		return dialog;
	}

	public static Dialog editDialog(Context ctx, String title, View v,
			String name1, OnClickListener listener1, String name2,
			OnClickListener listener2)
	{
		return editDialog(ctx, title, v, name1, listener1, name2, listener2,
				false);
	}

	public static Dialog messageDialog(Context ctx, String title,
			String content, String name1, OnClickListener listener1,
			String name2, OnClickListener listener2)
	{
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				ctx);

		TextView textView = new TextView(ctx);
		textView.setTextSize(20);
		textView.setPadding(20, 20, 20, 20);
		textView.setText(content);

		builder.setTitle(title);
		builder.setView(textView);
		builder.setPositiveButton(name1, listener1);
		builder.setNegativeButton(name2, listener2);
		dialog = builder.create();
		return dialog;
	}
}
