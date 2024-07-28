import React, { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import BodyParamsService from '../../services/bodyParams';
import AuthService from "../../services/auth.service";
import { withTranslation } from "react-i18next";
import { BodyParamDto } from "../../types/types";

type Props = {
  t: any;
  clientId: string | null;
};

type State = {
  dictBodyParam: string,
  value: string,
  bodyParameters: { id: string; name: string }[];
};

class PutBodyParam extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);

    this.state = {
      dictBodyParam: '',
      value: '',
      bodyParameters: []
    };
  }

  componentDidMount() {
    BodyParamsService.getDictBodyParams().then(
      response => {
        this.setState({
          bodyParameters: response.data
        });
      }
    );
  }

  validationSchema() {
    return Yup.object().shape({
      dictBodyParam: Yup.number().required("This field is required!"),
      value: Yup.number().required("This field is required!"),
    });
  }

  handlePost(formValue: { dictBodyParam: string; value: string }) {
    const { dictBodyParam, value } = formValue;
    const { clientId } = this.props;

    const bodyParamDto: BodyParamDto = {
      dict_body_params_id: Number(dictBodyParam),
      value: value,
      clientId: clientId || ''
    };

    BodyParamsService.postBodyParam(bodyParamDto);
  }

  render() {
    const { t } = this.props;

    const initialValues = {
      dictBodyParam: this.state.dictBodyParam,
      value: this.state.value
    };

    return (
      <div>
        <Formik
          initialValues={initialValues}
          validationSchema={this.validationSchema}
          onSubmit={this.handlePost}
        >
          <Form>
            <div style={{ display: 'flex', marginBottom: '20px' }}>
              <div style={{ marginBottom: '10px', marginRight: '10px' }}>
                <Field as="select" name="dictBodyParam" className="form-control">
                  <option value="" disabled>
                    {t("buttons.select_attribute")}
                  </option>
                  {this.state.bodyParameters.map((param) => (
                    <option key={param.id} value={param.id}>
                      {param.name}
                    </option>
                  ))}
                </Field>
                <ErrorMessage name="dictBodyParam" component="div" className="error" />
              </div>
              <div style={{ flex: 1, marginRight: '10px' }}>
                <Field name="value" type="text" className="form-control" />
                <ErrorMessage name="value" component="div" className="error" />
              </div>
              <div style={{ flex: 1 }}>
                <button type="submit" className="btn btn-primary btn-block">
                  <span>Add</span>
                </button>
              </div>
            </div>
          </Form>
        </Formik>
      </div>
    );
  }
}
export default withTranslation("global")(PutBodyParam);
