class BootStrap {

    def init = { servletContext ->
        def i = new Item(urtID:1, name:"Ka-Bar Knife")
        new Item(urtID:2, name:"Beretta", binding:"F", mode:0).save()
        new Item(urtID:3, name:"Desert Eagle", binding:"G", mode:0).save()
        new Item(urtID:4, name:"SPAS 12", binding:"H", mode:0).save()
        new Item(urtID:5, name:"MP5K", binding:"I", mode:0).save()
        new Item(urtID:6, name:"UMP45", binding:"J", mode:0).save()
        new Item(urtID:7, name:"HK69", binding:"K", mode:0).save()
        new Item(urtID:8, name:"300ML", binding:"L", mode:0).save()
        new Item(urtID:9, name:"G36", binding:"M", mode:0).save()
        new Item(urtID:10, name:"PSG-1", binding:"N", mode:0).save()
        new Item(urtID:11, name:"High Explosive Grenade", binding:"O", mode:0).save()
        
        new Item(urtID:13, name:"Smoke Grenade", binding:"Q", mode:0).save()
        new Item(urtID:14, name:"SR8", binding:"Z", mode:0).save()
        new Item(urtID:15, name:"AK-103", binding:"a", mode:0).save()
        new Item(urtID:16, name:"Negev", binding:"c", mode:0).save()

        new Item(urtID:17, name:"Kevlar Vest", binding:"R", mode:0).save()
        new Item(urtID:18, name:"Kevlar Helmet", binding:"W", mode:0).save()
        new Item(urtID:19, name:"Silencer", binding:"U", mode:0).save()
        new Item(urtID:20, name:"Laser sight", binding:"V", mode:0).save()
        new Item(urtID:21, name:"Medkit", binding:"T", mode:0).save()
        new Item(urtID:22, name:"NVGs", binding:"S", mode:0).save()
        new Item(urtID:23, name:"Extra Ammo", binding:"X", mode:0).save()
    }

    def destroy = {
    }
} 