# https

## 工具

- keytool 
- openssl
- portecle

## 注意事项
- Java平台默认识别jks格式的证书文件，但是android平台只识别bks格式的证书文件。
- 在以下命令提示 first and last name,务必填写你放问的ip地址 或者域名，比如localhost/127.0.0.1
```
hostname[username:/this/is/a/path][640]% keytool -keystore server.keystore -genkeypair -alias hostname
Enter keystore password:Re-enter new password:What is your first and last name?
  [Unknown]:  hostname
What is the name of your organizational unit?
  [Unknown]:  hostname
What is the name of your organization?
  [Unknown]:  hostname
What is the name of your City or Locality?
  [Unknown]:  hostname
What is the name of your State or Province?
  [Unknown]:  hostname
What is the two-letter country code for this unit?
  [Unknown]:  CA
Is CN=hostname, OU=hostname, O=hostname, L=hostname, ST=hostname, C=CA correct?
  [no]:  yes

Enter key password for <hostname>
        (RETURN if same as keystore password):
hostname[username:/this/is/a/path][641]%
```

## 生成证书 

生成server证书
```
keytool -genkey -alias zchao_server -keyalg RSA -keystore zchao_server.jks -validity 3600 -storepass 123456
```
签发server证书
```
keytool -export -alias zchao_server -file zchao_server.cer -keystore zchao_server.jks -storepass 123456
```
生成client证书
```
keytool -genkey -alias zchao_client -keyalg RSA -keystore zchao_client.jks -validity 3600 -storepass 123456
```
签发client证书
```
keytool -export -alias zchao_client -file zchao_client.cer -keystore zchao_client.jks -storepass 123456
```
zchao_client.cer执行以下步骤，将证书添加到kjs文件中
```
keytool -import -alias zc_client -file zc_client.cer -keystore zc_client_for_sever.jks
```

## 转换证书格式 jks to bks

- 去Portecle下载Download portecle-1.9.zip (3.4 MB)。
  [https://sourceforge.net/projects/portecle/files/latest/download?source=files]

## 使用openssl 证书格式转换 jks to pem

- 指定源(jks)文件和目标(pkcs)文件的文件名和类型;执行时输入设置给pkcs12证书的密码, 以及jks证书的密码;再通过openssl将pkcs12文件导出成pem格式文件.
- jks to p12
```
keytool -importkeystore -srckeystore tankywoo.jks -destkeystore tankywoo.p12 -srcstoretype jks -deststoretype pkcs12
```
- p12 to pem
```
# 生成key 加密的pem证书  使用加密的证书
# 测试okhttp单向认证时，需要使用加密证书，非加密证书会报错
openssl pkcs12 -in tankywoo.p12 -out tankywoo.pem    
```
```
# 生成key 非加密的pem证书
openssl pkcs12 -nodes -in tankywoo.p12 -out tankywoo.pem
```

### 分开导出

```
# 生成加密的key
openssl pkcs12 -in tankywoo.p12 -nocerts -out server.key
```
```
# 生成非加密的key
openssl pkcs12-in tankywoo.p12 -nocerts -nodes -out server.key
```
-  导出server证书
```
- openssl pkcs12 -in tankywoo.p12  -nokeys -clcerts -out server.crt
```
-  导出ca证书
```
openssl pkcs12 -in tankywoo.p12 -nokeys -clcerts -out server.crt
```

## httpclient 单向认证





