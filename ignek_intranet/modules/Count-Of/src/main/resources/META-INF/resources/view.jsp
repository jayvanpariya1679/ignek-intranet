<%@ include file="init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<portlet:defineObjects />
<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@ page import="com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil"%>
<%@ page import="com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil;" %>
<link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
<style>
   .header-body {
   margin-right: 40px;
   width: 255px;
   height: 100px;
   border-radius: 8px;
   background-color: #F0F1F1;
   }
   .employees{
   margin-left: 20px;
   margin-top: 10px;
   }
   .letters {
   margin-top: 5px;
   margin-left: 20px;
   font-family: Poppins;
   font-size: 14px;
   font-weight: 500;
   line-height: 21px;
   letter-spacing: 0em;
   color: #6C6C6C;
   }
   .numbers {
   margin-left: 179px;
   font-family: Poppins;
   font-size: 30px;
   font-weight: 900;
   line-height: 45px;
   letter-spacing: 0em;    
   text-align: left;
   }
   .horizontal-containers {
   display: flex;
   }
</style>
<div class="horizontal-containers">
   <div class="header-body">
      <div class="employees">
         <% 
            if(card.equals("Employees")) {
                out.print("<svg width='48' height='19' viewBox='0 0 48 19' fill='none' xmlns='http://www.w3.org/2000/svg'>" +
                    "<path d='M45.912 5.01844L26.037 0.968086C24.7013 0.696266 23.2995 0.696266 21.9638 0.968086L2.08875 5.01844C0.8205 5.27703 0 5.98234 0 6.81529C0 7.64825 0.8205 8.35356 2.088 8.61214L4.31625 9.06597C4.06875 9.35811 3.88575 9.67248 3.7665 10.002C2.96475 10.2468 2.4 10.7541 2.4 11.3535C2.4 11.9553 2.96775 12.4654 3.77475 12.7088L2.421 17.9968C2.30025 18.4691 2.71425 18.9172 3.2715 18.9172H6.32775C6.885 18.9172 7.29975 18.4691 7.17825 17.9968L5.82525 12.7088C6.63225 12.4654 7.2 11.9553 7.2 11.3535C7.2 10.8524 6.78825 10.4298 6.19275 10.1542C6.3045 9.9339 6.47775 9.73535 6.6855 9.54862L10.6958 10.366L9.6 15.8917C9.6 17.5628 16.047 18.9172 24 18.9172C31.953 18.9172 38.4 17.5628 38.4 15.8917L37.3043 10.366L45.912 8.61167C47.1795 8.35356 48 7.64825 48 6.81529C48 5.98234 47.1795 5.27703 45.912 5.01844ZM35.961 15.7906C35.154 16.3262 31.053 17.4045 24 17.4045C16.947 17.4045 12.846 16.3262 12.039 15.7906L13.0208 10.8397L21.9638 12.662C22.1588 12.7017 23.8928 13.0984 26.037 12.662L34.98 10.8397L35.961 15.7906ZM45.171 7.17362L25.296 11.224C24.4478 11.397 23.5523 11.397 22.704 11.224L9.52875 8.53887L24.2205 6.80206C24.8723 6.72548 25.3013 6.33027 25.179 5.91947C25.0575 5.50819 24.417 5.23921 23.7795 5.31579L8.496 7.12162C7.566 7.2313 6.73275 7.48326 6.015 7.82268L2.82825 7.17315C2.2395 7.05261 2.26875 6.57089 2.82825 6.45696L22.7033 2.40661C23.8358 2.17591 24.7913 2.30402 25.2952 2.40661L45.1703 6.45696C45.7245 6.56995 45.7635 7.05261 45.171 7.17362Z' fill='black'/>" +
                    "</svg>");
            } else if(card.equals("Technologies")) {
            	out.print("<svg width='28' height='24' viewBox='0 0 28 24' fill='none' xmlns='http://www.w3.org/2000/svg'>"+
            		"<path d='M3.5 0.73877H24.5C26.433 0.73877 28 1.67447 28 2.82874V23.0318L14 18.1552L0 23.0318V2.82874C0 1.67447 1.56698 0.73877 3.5 0.73877ZM2.33333 20.606L14 16.5422L25.6667 20.606V2.82874C25.6667 2.44462 25.1433 2.13208 24.5 2.13208H3.5C2.85673 2.13208 2.33333 2.44462 2.33333 2.82874V20.606Z' fill='black'/>"+
            	"</svg>");
            } else if(card.equals("Images")) {
            	out.print("<svg width='28' height='24' viewBox='0 0 28 24' fill='none' xmlns='http://www.w3.org/2000/svg'>"+
                		"<path d='M3.5 0.73877H24.5C26.433 0.73877 28 1.67447 28 2.82874V23.0318L14 18.1552L0 23.0318V2.82874C0 1.67447 1.56698 0.73877 3.5 0.73877ZM2.33333 20.606L14 16.5422L25.6667 20.606V2.82874C25.6667 2.44462 25.1433 2.13208 24.5 2.13208H3.5C2.85673 2.13208 2.33333 2.44462 2.33333 2.82874V20.606Z' fill='black'/>"+
                    	"</svg>");
            } else if(card.equals("Users")) {
            	out.print("<svg width='34' height='23' viewBox='0 0 34 23' fill='none' xmlns='http://www.w3.org/2000/svg'>"+
            	"<path d='M17 2.09227C21.1039 2.09227 24.4375 4.21558 24.4375 6.82953C24.4375 9.44349 21.1039 11.5668 17 11.5668C12.8961 11.5668 9.5625 9.44349 9.5625 6.82953C9.5625 4.21558 12.8961 2.09227 17 2.09227ZM25.5 15.6273C29.0129 15.6273 31.875 17.4503 31.875 19.6878V21.0413H2.125V19.6878C2.125 17.4503 4.98711 15.6273 8.5 15.6273C14.1445 15.6273 12.9691 16.3041 17 16.3041C21.0441 16.3041 19.8488 15.6273 25.5 15.6273ZM17 0.73877C11.7207 0.73877 7.4375 3.46692 7.4375 6.82953C7.4375 10.1921 11.7207 12.9203 17 12.9203C22.2793 12.9203 26.5625 10.1921 26.5625 6.82953C26.5625 3.46692 22.2793 0.73877 17 0.73877ZM25.5 14.2738C19.3641 14.2738 20.7852 14.9506 17 14.9506C13.2281 14.9506 14.6293 14.2738 8.5 14.2738C3.80508 14.2738 0 16.6974 0 19.6878V21.0413C0 21.79 0.949609 22.3948 2.125 22.3948H31.875C33.0504 22.3948 34 21.79 34 21.0413V19.6878C34 16.6974 30.1949 14.2738 25.5 14.2738Z' fill='black'/>"+
            	"</svg>");
            } %>
      </div>
      <div class="letters">
         <%=card%>
      </div>
      <div class="numbers">
         <% int number=0; 
            if(card.equals("Employees")){out.print(UserLocalServiceUtil.getRoleUsersCount(RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId() , "Employee Role").getRoleId()));}
             else if(card.equals("Technologies")){out.print(AssetCategoryLocalServiceUtil.getAssetCategoriesCount());} 
             else if(card.equals("Images")){out.print(DLFileEntryLocalServiceUtil.getDLFileEntriesCount());} 
             else if(card.equals("Users")){out.print(UserLocalServiceUtil.getUsersCount());} %>
      </div>
   </div>
</div>