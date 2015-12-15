<#if subMenuList??>
    <#list subMenuList as menu>
        <li>
            <a <#if menu.menuPath?? && menu.menuPath!="" && menu.menuPath!="/">href="${menu.menuPath}" <#if menu.description=="dialog"> target="dialog" rel="dlg_page${menu.menuId}"</#if> target="navTab" rel="main"</#if>>${menu.menuName}</a>
            <#assign parentMenuId = menu.menuId/>
            <#if menu.subMenu?? && menu.subMenu?size gt 0>
                <#assign subMenuList = menu.subMenu/>
                <ul>
                    <#include "menutree.ftl"/>
                </ul>
            </#if>
        </li>   
    </#list>
</#if>
