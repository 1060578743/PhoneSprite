/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\980008027\\workspace\\PhoneScript2\\src\\com\\lym\\xposed\\aidl\\IView.aidl
 */
package com.lym.xposed.aidl;
public interface IView extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.lym.xposed.aidl.IView
{
private static final java.lang.String DESCRIPTOR = "com.lym.xposed.aidl.IView";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.lym.xposed.aidl.IView interface,
 * generating a proxy if needed.
 */
public static com.lym.xposed.aidl.IView asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.lym.xposed.aidl.IView))) {
return ((com.lym.xposed.aidl.IView)iin);
}
return new com.lym.xposed.aidl.IView.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_select:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.lym.xposed.aidl.IView _result = this.select(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectWithSplit:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
com.lym.xposed.aidl.IView _result = this.selectWithSplit(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectId:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectId(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectIdIndex:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectIdIndex(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectClass:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.lym.xposed.aidl.IView _result = this.selectClass(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectClassIndex:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectClassIndex(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectText:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.lym.xposed.aidl.IView _result = this.selectText(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectTextIndex:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectTextIndex(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectContains:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.lym.xposed.aidl.IView _result = this.selectContains(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectContainsIndex:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectContainsIndex(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_selectChild:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.lym.xposed.aidl.IView _result = this.selectChild(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_exist:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.exist();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isVisible:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isVisible();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getText:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getText();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getId:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getId();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getWidth:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getWidth();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getHeight:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getHeight();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getX:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getX();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getY:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getY();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getChildCount:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getChildCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setText:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setText(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_click:
{
data.enforceInterface(DESCRIPTOR);
this.click();
reply.writeNoException();
return true;
}
case TRANSACTION_longClick:
{
data.enforceInterface(DESCRIPTOR);
this.longClick();
reply.writeNoException();
return true;
}
case TRANSACTION_scroll:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.scroll(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.lym.xposed.aidl.IView
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public com.lym.xposed.aidl.IView select(java.lang.String select) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(select);
mRemote.transact(Stub.TRANSACTION_select, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectWithSplit(java.lang.String select, java.lang.String split1, java.lang.String split2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(select);
_data.writeString(split1);
_data.writeString(split2);
mRemote.transact(Stub.TRANSACTION_selectWithSplit, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectId(int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_selectId, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectIdIndex(int id, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_selectIdIndex, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectClass(java.lang.String clz) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(clz);
mRemote.transact(Stub.TRANSACTION_selectClass, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectClassIndex(java.lang.String clz, int i) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(clz);
_data.writeInt(i);
mRemote.transact(Stub.TRANSACTION_selectClassIndex, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectText(java.lang.String text) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
mRemote.transact(Stub.TRANSACTION_selectText, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectTextIndex(java.lang.String text, int i) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
_data.writeInt(i);
mRemote.transact(Stub.TRANSACTION_selectTextIndex, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectContains(java.lang.String text) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
mRemote.transact(Stub.TRANSACTION_selectContains, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectContainsIndex(java.lang.String text, int i) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
_data.writeInt(i);
mRemote.transact(Stub.TRANSACTION_selectContainsIndex, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.lym.xposed.aidl.IView selectChild(int i) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lym.xposed.aidl.IView _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(i);
mRemote.transact(Stub.TRANSACTION_selectChild, _data, _reply, 0);
_reply.readException();
_result = com.lym.xposed.aidl.IView.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean exist() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_exist, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isVisible() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isVisible, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getText() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getText, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getId, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getWidth() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWidth, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getHeight() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getHeight, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getX() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getX, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getY() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getY, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getChildCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getChildCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setText(java.lang.String text) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
mRemote.transact(Stub.TRANSACTION_setText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void click() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_click, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void longClick() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_longClick, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void scroll(int dis, int time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(dis);
_data.writeInt(time);
mRemote.transact(Stub.TRANSACTION_scroll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_select = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_selectWithSplit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_selectId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_selectIdIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_selectClass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_selectClassIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_selectText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_selectTextIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_selectContains = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_selectContainsIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_selectChild = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_exist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_isVisible = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getWidth = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getHeight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getX = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getY = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getChildCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_setText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_click = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_longClick = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_scroll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
}
public com.lym.xposed.aidl.IView select(java.lang.String select) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectWithSplit(java.lang.String select, java.lang.String split1, java.lang.String split2) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectId(int id) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectIdIndex(int id, int index) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectClass(java.lang.String clz) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectClassIndex(java.lang.String clz, int i) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectText(java.lang.String text) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectTextIndex(java.lang.String text, int i) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectContains(java.lang.String text) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectContainsIndex(java.lang.String text, int i) throws android.os.RemoteException;
public com.lym.xposed.aidl.IView selectChild(int i) throws android.os.RemoteException;
public boolean exist() throws android.os.RemoteException;
public boolean isVisible() throws android.os.RemoteException;
public java.lang.String getText() throws android.os.RemoteException;
public int getId() throws android.os.RemoteException;
public int getWidth() throws android.os.RemoteException;
public int getHeight() throws android.os.RemoteException;
public int getX() throws android.os.RemoteException;
public int getY() throws android.os.RemoteException;
public int getChildCount() throws android.os.RemoteException;
public void setText(java.lang.String text) throws android.os.RemoteException;
public void click() throws android.os.RemoteException;
public void longClick() throws android.os.RemoteException;
public void scroll(int dis, int time) throws android.os.RemoteException;
}
