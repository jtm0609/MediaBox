package com.example.data

interface DataMapper<DomainModel> {

    fun toDomain(): DomainModel
}