<li class="list-group-item {{#expired}}text-muted expired{{/expired}} ticket-category-restricted-{{accessRestricted}}">
    <div class="form-group">
        <div class="col-sm-7" id="{{id}}-label">
            <label class="control-label category-name">{{name}}</label>
            {{^expired}}
                <div>
                    {{#saleInFuture}}
                        {{#i18n}}show-event.sales-not-started [{{#format-date}}{{zonedInception}} dd.MM.yyyy HH:mm locale:{{#i18n}}locale{{/i18n}}{{/format-date}}]{{/i18n}}
                    {{/saleInFuture}}
                    {{^saleInFuture}}
                        {{#i18n}}show-event.sales-end [{{#format-date}}{{zonedExpiration}} dd.MM.yyyy HH:mm locale:{{#i18n}}locale{{/i18n}}{{/format-date}}]{{/i18n}}
                    {{/saleInFuture}}
                </div>
            {{/expired}}
            {{#expired}}
                <div>
                    {{#i18n}}show-event.sales-ended [{{#format-date}}{{zonedExpiration}} dd.MM.yyyy HH:mm locale:{{#i18n}}locale{{/i18n}}{{/format-date}}]{{/i18n}}
                </div>
            {{/expired}}
            <div>
                {{#commonmark}}{{description}}{{/commonmark}}
            </div>
        </div>
        <div class="col-sm-3">
            <label class="control-label">
                {{#free}}
                    {{#i18n}}common.free{{/i18n}}
                {{/free}}
                {{^free}}
                    {{#expired}}
                        {{formattedFinalPrice}} {{currencyCode}}
                    {{/expired}}
                    {{^expired}}
                        {{#supportsDiscount}}
                            <del>{{formattedFinalPrice}}</del>
                            <mark>{{discountedPrice}}</mark>
                            {{currencyCode}}
                        {{/supportsDiscount}}
                        {{^supportsDiscount}}
                            {{formattedFinalPrice}} {{currencyCode}}
                        {{/supportsDiscount}}
                        <i class="fa fa-info-circle text-info tooltip-handler" title="{{#event.vatIncluded}}{{#i18n}}show-event.incVat [{{event.vat}}] [{{vatTranslation}}]{{/i18n}}{{/event.vatIncluded}}{{^event.vatIncluded}}{{#i18n}}show-event.excVat [{{event.vat}}] [{{vatTranslation}}]{{/i18n}}{{/event.vatIncluded}}"></i>
                    {{/expired}}
                {{/free}}
            </label>
        </div>
        <div class="col-sm-2 text-align-center">
            {{#saleableAndLimitNotReached}}
                <input name="reservation[{{-index}}].ticketCategoryId" value="{{id}}" type="hidden">
                <select aria-labelledby="{{id}}-label" name="reservation[{{-index}}].amount" class="form-control text-align-center" placeholder="0" autocomplete="off" data-ticket-selector>
                        {{#amountOfTickets}}<option value="{{this}}">{{this}}</option>{{/amountOfTickets}}
                </select>
            {{/saleableAndLimitNotReached}}
            {{^saleableAndLimitNotReached}}
                <label class="control-label">
                    {{#souldOutOrLimitReached}}
                        {{#i18n}}show-event.sold-out{{/i18n}}
                    {{/souldOutOrLimitReached}}
                    {{^souldOutOrLimitReached}}
                        {{#i18n}}show-event.not-available{{/i18n}}
                    {{/souldOutOrLimitReached}}
                </label>
            {{/saleableAndLimitNotReached}}
        </div>
    </div>
</li>