const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            arrayAccounts:[],
            amount:"",
            description:"",
            cuentaOrigen:"",
            cuentaDestino:"",
            tipoTransferencia:"",
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.arrayAccounts=res.data.accounts.filter(e=>e.active==true)
             
            })
    },
    methods:{
        saldo(cuenta){
            prueba=this.arrayAccounts.filter((e=>e.number==cuenta))
            return prueba[0].balance
        },
        CrearTransferencia(){
            
            Swal.fire({
                title: '¿Desea realizar la transferencia?',
                text: "Una vez realizada, no se podrá revertir",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/transactions',
                    "amount="+this.amount+"&"+"description="+this.description+"&"+"numberOrigin="+this.cuentaOrigen+"&"+"numberDestiny="+this.cuentaDestino,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(res => Swal.fire(
                   'Transferencia realizada con exito!',
                    'Puedes chequear tu cuenta.',
                    'success',))
                    .then(res=>location.reload())
                    .catch(res => Swal.fire(res.response.data,"Intente nuevamente","error"))
                  
                }
              })
            
            
        },
        

        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 