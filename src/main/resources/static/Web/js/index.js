const app = Vue.createApp({
    data (){
        return {
            firstName:"",
            lastName:"",
            userNameCreated:"",
            passwordCreated:"",
            username:"",
            password:"",
            vistaSingIn:true,
            
        };
    },
  

        methods:{
            CambiarVista(){
                if(this.vistaSingIn==true){
                    this.vistaSingIn=false
                }
                else
                this.vistaSingIn=true
                
            },
            enviarDatos(){
               
                axios.post('/api/login',
                "email="+this.username+"&"+"password="+this.password,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => window.location.href = "accounts.html")
                .catch(response=> swal("Datos ingresados incorrectos","Intenta nuevamente","error"))
                 
                },     
              
                crearCuenta(){
               
                    axios.post('/api/clients',
                    "firstName="+this.firstName+"&"+"lastName="+this.lastName+"&"+"email="+this.userNameCreated+"&"+"password="+this.passwordCreated,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(response=> axios.post('/api/login',
                    "email="+this.userNameCreated+"&"+"password="+this.passwordCreated,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}}))
                    .then(response => window.location.href = "accounts.html")
                    .catch(response=> swal("Datos ingresados incorrectos","Intenta nuevamente","error"))
                     
                } 
            }
    });

    app.mount("#app"); 

