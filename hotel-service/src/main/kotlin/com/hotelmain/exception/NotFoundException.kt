package com.hotelmain.exception

class NotFoundException(entityName: String, id: Long) : RuntimeException("$entityName with id $id not found") {
}