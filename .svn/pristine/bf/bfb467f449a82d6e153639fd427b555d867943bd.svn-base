<#assign deep = deep + 1/>
<#list subMenuList as menu>
    <#assign index = index+1/>
    <tr>
        <td><#list 1 .. deep as i><#if i == 1>├─<#else>──</#if></#list><input type="checkbox" name="menuIds" id="menuid${index}" value="${menu.menuId}" <#if menuIdList?seq_contains(menu.menuId)>checked</#if>/><label for="menuid${index}">${menu.menuName}</label></td>
    </tr>
    <#assign subMenuList = menu.subMenu/>
    <#include "grantmenu.ftl"/>
</#list>
<#assign deep = deep - 1/>