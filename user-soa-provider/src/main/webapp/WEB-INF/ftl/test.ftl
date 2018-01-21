<#-- @ftlvariable name="customer" type="com.hnair.consumer.user.model.Customers" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我爱大爽</title>
</head>
<body>
body内容
<hr>
我爱~:<#if name??>${name}</#if>

<#if customer??>
    <#if customer.address??>
        ${customer.address}<br>
    </#if>

    <#if customer.cid??>
        ${customer.cid}<br>
    </#if>

    <#if customer.idNumber??>
        ${customer.idNumber}<br>
    </#if>

    <#if customer.mobile??>
        ${customer.mobile}<br>
    </#if>

    <#if customer.name??>
        ${customer.name}<br>
    </#if>


</#if>
</body>
</html>