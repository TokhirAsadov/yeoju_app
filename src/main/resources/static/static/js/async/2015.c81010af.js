/*! For license information please see 2015.c81010af.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["2015"],{66440:function(e,t,a){"use strict";a.r(t),a.d(t,{default:function(){return R}});var l=a("7409"),n=a("99282"),d=a("38956"),i=a("58865");a("21249"),a("57640"),a("9924"),a("92222");var u=a("85893"),r=a("67294"),s=a("71893"),c=a("28685"),o=a("8193"),h=a("7104"),f=a("55693"),m=a("42154"),x=a("44025"),p=a("61261"),S=a("16356"),g=a("27233"),v=a("50533"),j=a("32392"),A=a("72132"),b=a("94718"),E=a("47516"),I=a("39711"),N=a("71632");function C(){var e=(0,i._)(["\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n\n  p {\n    color: ",";\n    font-size: 30px;\n    font-weight: bold;\n    ","\n  }\n"]);return C=function(){return e},e}function _(){var e=(0,i._)(["\n  width: 100%;\n  padding: 1rem;\n"]);return _=function(){return e},e}var w=s.default.h1(C(),c.mainColor,(0,N.extrasmall)({textAlign:"center",fontSize:"20px"})),D=s.default.div(_()),R=function(){var e,t=["TEACHING","FINISHED","ACADEMIC_VACATION","EXPELLED_FROM_UNIVERSITY","ACADEMIC_DEBTOR","TRANSFER"],a=(0,c.getHeaders)().headers,i=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"fullName",headerName:"Full Name",width:400,editable:!1},{field:"groupName",headerName:"Group",width:100,editable:!1},{field:"passport",headerName:"Passport",width:150,editable:!1},{field:"login",headerName:"Login",width:200,editable:!1},{field:"rfid",headerName:"RFID",width:200,editable:!1},{field:"rektororder",headerName:"Rektors order",width:150,editable:!1},{field:"changeStatus",headerName:"Change Status",width:180,editable:!1,renderCell:function(e){return(0,u.jsx)(f.default,{sx:{width:"180px"},children:(0,u.jsx)(m.default,{fullWidth:!0,children:(0,u.jsx)(x.default,{size:"small",labelId:"demo-simple-select-label",id:"demo-simple-select",onChange:function(t){return M(t,e)},children:t.map(function(e){return(0,u.jsx)(p.default,{value:e,children:e},e)})})})})}},{field:"teachStatus",headerName:"Status",width:180,editable:!1}],s=(0,v.useSelector)(function(e){var t;return null==e?void 0:null===(t=e.dekanat)||void 0===t?void 0:t.dekanat})||null,N=(0,d._)((0,r.useState)([]),2),C=N[0],_=N[1],R=(0,d._)((0,r.useState)(""),2),k=R[0],T=R[1],F=(0,d._)((0,r.useState)(""),2),y=F[0],B=F[1],L=(0,I.useNavigate)();function O(){""===y&&""===k?g.default.get(c.BASE_URL+"/student/getAllStudentDataForDean",{headers:a}).then(function(e){_(e.data)}).catch(function(e){}):""!==y&&""===k?g.default.get(c.BASE_URL+"/student/getStudentDataForTeachStatusAllByFacultyId?facultyId=".concat(y),{headers:a}).then(function(e){_(e.data)}).catch(function(e){}):""!==k&&""!==y&&g.default.get(c.BASE_URL+"/student/getStudentDataForTeachStatusAndFacultyId?teachStatus=".concat(k,"&facultyId=").concat(y),{headers:a}).then(function(e){_(e.data)}).catch(function(e){})}(0,r.useEffect)(function(){O()},[k,y]);var M=function(e,t){var a=e.target.value,l=t.id;g.default.get(c.BASE_URL+"/student/changeTeachStatusOfStudent/".concat(l,"?teachStatus=").concat(a)).then(function(e){O(),A.toast.success("Success change status")}).catch(function(e){A.toast.error("Error change status")})};return(0,u.jsxs)(D,{children:[(0,u.jsxs)(w,{children:[(0,u.jsxs)("p",{children:[(0,u.jsx)(o.AiOutlineUnorderedList,{})," Archive"]}),(0,u.jsx)(b.default,{onClick:function(){return L(-1)},variant:"outlined",startIcon:(0,u.jsx)(E.BiArrowBack,{}),children:"Back"})]}),(0,u.jsxs)(f.default,{sx:{mt:"15px",display:"flex",gap:"20px"},children:[(0,u.jsx)(f.default,{sx:{width:150},children:(0,u.jsxs)(m.default,{fullWidth:!0,children:[(0,u.jsx)(j.default,{id:"demo-simple-select-label",children:"Yo'nalish"}),(0,u.jsxs)(x.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:y,label:"Yo'nalish",onChange:function(e){B(e.target.value)},children:[(0,u.jsx)(p.default,{value:"",children:(0,u.jsx)("em",{children:"All"})}),null==s?void 0:null===(e=s.faculties)||void 0===e?void 0:e.map(function(e){return(0,u.jsx)(p.default,{value:null==e?void 0:e.id,children:null==e?void 0:e.shortName},null==e?void 0:e.id)})]})]})}),(0,u.jsx)(f.default,{sx:{width:150},children:(0,u.jsxs)(m.default,{fullWidth:!0,children:[(0,u.jsx)(j.default,{id:"demo-simple-select-label",children:"Status"}),(0,u.jsxs)(x.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:k,label:"Status",onChange:function(e){T(e.target.value)},children:[(0,u.jsx)(p.default,{value:"",children:(0,u.jsx)("em",{children:"All"})}),["FINISHED","ACADEMIC_VACATION","EXPELLED_FROM_UNIVERSITY","ACADEMIC_DEBTOR","TRANSFER"].map(function(e){return(0,u.jsx)(p.default,{value:e,children:e},e)})]})]})})]}),(0,u.jsx)(h.Card,{sx:{mt:3},children:(0,u.jsx)(h.CardContent,{children:(0,u.jsx)(S.DataGrid,{checkboxSelection:!0,style:{width:"100%",minHeight:"300px!important"},columns:i,rows:C,components:{Toolbar:S.GridToolbar},autoHeight:!0,pageSize:20,initialState:(0,n._)((0,l._)({},C.initialState),{columns:{columnVisibilityModel:{id:!1,cardNo:!1,login:!1,email:!1,passport:!1,rektororder:!1,rfid:!1,changeStatus:!1}}})})})})]})}}}]);