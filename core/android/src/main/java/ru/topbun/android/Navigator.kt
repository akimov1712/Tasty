package ru.topbun.android

import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

@OptIn(InternalVoyagerApi::class)
fun Navigator.pushFront(screen: Screen){
    val items = this.items.toMutableSet()
    items.remove(screen)
    items.remove(this.items.first())
    this.popAll()
    this.dispose(screen)
    items.add(screen)
    this.push(items.toList())
}
